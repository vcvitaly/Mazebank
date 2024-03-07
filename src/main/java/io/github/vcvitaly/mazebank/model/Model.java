package io.github.vcvitaly.mazebank.model;

import io.github.vcvitaly.mazebank.util.DateUtil;
import io.github.vcvitaly.mazebank.view.ViewFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Model {
    private static final int LATEST_TRANSACTIONS_LIMIT = 4;
    private static final int NO_LIMIT_CONST = -1;
    @Getter
    private final ViewFactory viewFactory;
    @Getter
    private final DatabaseDriver databaseDriver;
    // Client data section
    @Getter
    private final Client client;
    @Getter
    private final ObservableList<Transaction> latestTransactions;
    @Getter
    private final ObservableList<Transaction> allTransactions;
    // Admin section data
    @Getter
    private final ObservableList<Client> clients;

    private Model() {
        viewFactory = new ViewFactory();
        databaseDriver = new DatabaseDriver();
        // Client data section
        client = new Client("", "", "", null, null, null);
        latestTransactions = FXCollections.observableArrayList();
        allTransactions = FXCollections.observableArrayList();
        // Admin data section
        clients = FXCollections.observableArrayList();
    }

    // Client method section
    public boolean evaluateClientCredentials(String payeeAddress, String password) {
        CheckingAccount checkingAccount;
        SavingAccount savingAccount;
        final ResultSet resultSet = databaseDriver.getClientData(payeeAddress, password);
        try {
            if (resultSet.isBeforeFirst()) {
                client.getFirstName().set(resultSet.getString("FirstName"));
                client.getLastName().set(resultSet.getString("LastName"));
                client.getPayeeAddress().set(resultSet.getString("PayeeAddress"));
                final LocalDate date = DateUtil.parseDate(resultSet.getString("Date"));
                client.getDateCreated().set(date);
                checkingAccount = extractCheckingAccount(databaseDriver.getCheckingAccountData(payeeAddress));
                savingAccount = extractSavingAccount(databaseDriver.getSavingAccountData(payeeAddress));
                client.getCheckingAccount().set(checkingAccount);
                client.getSavingAccount().set(savingAccount);
                return true;
            } else {
                log.error("Unknown client: " + payeeAddress);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    
    public void setLatestTransactions() {
        prepareTransactions(latestTransactions, LATEST_TRANSACTIONS_LIMIT);
    }

    public void setAllTransactions() {
        prepareTransactions(allTransactions, NO_LIMIT_CONST);
    }

    private void prepareTransactions(ObservableList<Transaction> transactions, int limit) {
        transactions.clear();
        try (final ResultSet resultSet = databaseDriver.getTransactions(client.getPayeeAddress().get(), limit)) {
            while (resultSet.next()) {
                final String sender = resultSet.getString("Sender");
                final String receiver = resultSet.getString("Receiver");
                final double amount = resultSet.getDouble("Amount");
                final LocalDate date = DateUtil.parseDate(resultSet.getString("Date"));
                final String message = resultSet.getString("Message");
                transactions.add(
                        Transaction.builder()
                                .sender(sender)
                                .receiver(receiver)
                                .amount(amount)
                                .date(date)
                                .message(message)
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMoney(String receiver, double amount, String message) {
        final String sender = client.getPayeeAddress().get();
        try (final ResultSet resultSet = databaseDriver.searchClient(receiver)) {
            if (resultSet.isBeforeFirst()) {
                if (databaseDriver.subtractFromBalance(sender, amount)) {
                    client.getSavingAccount().get().setBalance(databaseDriver.getSavingAccountBalance(sender));
                    databaseDriver.addToBalance(receiver, amount);
                    databaseDriver.createNewTransaction(sender, receiver, amount, message);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Admin method section
    public boolean evaluateAdminCredentials(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try {
            if (resultSet.isBeforeFirst()) {
                return true;
            } else {
                log.error("Unknown admin: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void setClients() {
        CheckingAccount checkingAccount;
        SavingAccount savingAccount;
        try (final ResultSet clientsRs = databaseDriver.getAllClientData();
             final ResultSet checkingAccountsRs = databaseDriver.getAllCheckingAccountData();
             final ResultSet savingAccountsRs = databaseDriver.getAllSavingAccountData()) {
            final Map<String, CheckingAccount> checkingAccountByPayeeAddress = extractCheckingAccounts(checkingAccountsRs);
            final Map<String, SavingAccount> savingAccountByPayeeAddress = extractSavingAccounts(savingAccountsRs);
            while (clientsRs.next()) {
                final String firstName = clientsRs.getString("FirstName");
                final String lastName = clientsRs.getString("LastName");
                final String payeeAddress = clientsRs.getString("PayeeAddress");
                final LocalDate dateCreated = DateUtil.parseDate(clientsRs.getString("Date"));
                checkingAccount = checkingAccountByPayeeAddress.get(payeeAddress);
                savingAccount = savingAccountByPayeeAddress.get(payeeAddress);
                clients.add(
                        Client.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .payeeAddress(payeeAddress)
                                .checkingAccount(checkingAccount)
                                .savingAccount(savingAccount)
                                .dateCreated(dateCreated)
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Client> searchClient(String payeeAddress) {
        final ObservableList<Client> searchResult = FXCollections.observableArrayList();
        try (final ResultSet clientRs = databaseDriver.searchClient(payeeAddress)) {
            final String firstName = clientRs.getString("FirstName");
            final String lastName = clientRs.getString("LastName");
            final LocalDate dateCreated = DateUtil.parseDate(clientRs.getString("Date"));
            final CheckingAccount checkingAccount = extractCheckingAccount(databaseDriver.getCheckingAccountData(payeeAddress));
            final SavingAccount savingAccount = extractSavingAccount(databaseDriver.getSavingAccountData(payeeAddress));
            searchResult.add(
                    Client.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .payeeAddress(payeeAddress)
                            .checkingAccount(checkingAccount)
                            .savingAccount(savingAccount)
                            .dateCreated(dateCreated)
                            .build()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }

    private Map<String, CheckingAccount> extractCheckingAccounts(ResultSet resultSet) {
        Map<String, CheckingAccount> checkingAccountByPayeeAddress = new HashMap<>();
        try {
            while (resultSet.next()) {
                final CheckingAccount checkingAccount = extractCheckingAccount(resultSet);
                checkingAccountByPayeeAddress.put(
                        checkingAccount.getOwner().getValue(),
                        checkingAccount
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkingAccountByPayeeAddress;
    }

    private Map<String, SavingAccount> extractSavingAccounts(ResultSet resultSet) {
        Map<String, SavingAccount> savingAccountByPayeeAddress = new HashMap<>();
        try {
            while (resultSet.next()) {
                final SavingAccount savingAccount = extractSavingAccount(resultSet);
                savingAccountByPayeeAddress.put(
                        savingAccount.getOwner().getValue(),
                        savingAccount
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return savingAccountByPayeeAddress;
    }

    private CheckingAccount extractCheckingAccount(ResultSet resultSet) {
        try {
            final String accountNumber = resultSet.getString("AccountNumber");
            final int transactionNumLimit = (int) resultSet.getDouble("TransactionLimit");
            final double balance = resultSet.getDouble("Balance");
            final String owner = resultSet.getString("Owner");
            return CheckingAccount.builder()
                    .owner(owner)
                    .accountNumber(accountNumber)
                    .balance(balance)
                    .transactionNumLimit(transactionNumLimit)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SavingAccount extractSavingAccount(ResultSet resultSet) {
        try {
            final String accountNumber = resultSet.getString("AccountNumber");
            final double withdrawalLimit = resultSet.getDouble("WithdrawalLimit");
            final double balance = resultSet.getDouble("Balance");
            final String owner = resultSet.getString("Owner");
            return SavingAccount.builder()
                    .owner(owner)
                    .accountNumber(accountNumber)
                    .balance(balance)
                    .withdrawalLimit(withdrawalLimit)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Model getInstance() {
        return ModelHolder.model;
    }

    private static class ModelHolder {
        private static final Model model = new Model();
    }
}
