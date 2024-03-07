package io.github.vcvitaly.mazebank.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/vcvit/DEV/mazebank/mazebank.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Client section
    * */
    public ResultSet getClientData(String payeeAddress, String password) {
        return executeSelect("SELECT * FROM Clients WHERE PayeeAddress = '%s' AND Password = '%s'".formatted(payeeAddress, password));
    }

    public ResultSet getTransactions(String payeeAddress, int limit) {
        return executeSelect(
                "SELECT * FROM Transactions WHERE Sender = '%s' OR Receiver = '%s' LIMIT %d".formatted(payeeAddress, payeeAddress, limit)
        );
    }

    /*
    * Admin section
    * */
    public ResultSet getAdminData(String username, String password) {
        return executeSelect("SELECT * FROM Admins WHERE Username = '%s' AND Password = '%s'".formatted(username, password));
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        executeUpdate(
                """
                INSERT INTO Clients(FirstName, LastName, PayeeAddress, Password, Date)
                VALUES('%s', '%s', '%s', '%s', '%s')
                """.formatted(firstName, lastName, payeeAddress, password, date.toString())
        );
    }

    public void createCheckingAccount(String owner, String accountNumber, double transactionNumLimit, double balance) {
        executeUpdate(
                """
                INSERT INTO CheckingAccounts(Owner, AccountNumber, TransactionLimit, Balance)
                VALUES('%s', '%s', %f, %f)
                """.formatted(owner, accountNumber, transactionNumLimit, balance)
        );
    }

    public void createSavingAccount(String owner, String accountNumber, double withdrawalLimit, double balance) {
        executeUpdate(
                """
                INSERT INTO SavingsAccounts(Owner, AccountNumber, WithdrawalLimit, Balance)
                VALUES('%s', '%s', %f, %f)
                """.formatted(owner, accountNumber, withdrawalLimit, balance)
        );
    }

    public ResultSet getAllClientData() {
        return executeSelect("SELECT * FROM Clients");
    }

    public ResultSet searchClient(String payeeAddress) {
        return executeSelect("SELECT * FROM Clients WHERE PayeeAddress = '%s'".formatted(payeeAddress));
    }

    public void depositSavings(String payeeAddress, double amount) {
        executeUpdate("UPDATE SavingsAccounts SET Balance = %f WHERE Owner = '%s'".formatted(amount, payeeAddress));
    }

    /*
    * Utility methods
    * */
    public int getLastClientId() {
        try (final ResultSet resultSet = executeSelect("SELECT seq FROM sqlite_sequence WHERE name = 'Clients'")) {
            return resultSet.getInt("seq");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllCheckingAccountData() {
        return executeSelect("SELECT * FROM CheckingAccounts");
    }

    public ResultSet getAllSavingAccountData() {
        return executeSelect("SELECT * FROM SavingsAccounts");
    }

    public ResultSet getCheckingAccountData(String payeeAddress) {
        return executeSelect("SELECT * FROM CheckingAccounts WHERE Owner = '%s'".formatted(payeeAddress));
    }

    public ResultSet getSavingAccountData(String payeeAddress) {
        return executeSelect("SELECT * FROM SavingsAccounts WHERE Owner = '%s'".formatted(payeeAddress));
    }

    private ResultSet executeSelect(String sql) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    private void executeUpdate(String sql) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
