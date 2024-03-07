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
    @Getter
    private final ViewFactory viewFactory;
    @Getter
    private final DatabaseDriver databaseDriver;
    // Client data section
    @Getter
    private final Client client;
    // Admin section data
    @Getter
    private final ObservableList<Client> clients;

    private Model() {
        viewFactory = new ViewFactory();
        databaseDriver = new DatabaseDriver();
        // Client data section
        client = new Client("", "", "", null, null, null);
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
