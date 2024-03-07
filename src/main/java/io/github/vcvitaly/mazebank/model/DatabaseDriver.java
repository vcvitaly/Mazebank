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
        return getLoginData(payeeAddress, password, "SELECT * FROM Clients WHERE PayeeAddress = '%s' AND Password = '%s'");
    }

    /*
    * Admin section
    * */
    public ResultSet getAdminData(String username, String password) {
        return getLoginData(username, password, "SELECT * FROM Admins WHERE Username = '%s' AND Password = '%s'");
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(
            """
            INSERT INTO Clients(FirstName, LastName, PayeeAddress, Password, Date)
            VALUES('%s', '%s', '%s', '%s', '%s')
            """.formatted(firstName, lastName, payeeAddress, password, date.toString())
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCheckingAccount(String owner, String accountNumber, double transactionNumLimit, double balance) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(
            """
            INSERT INTO CheckingAccounts(Owner, AccountNumber, TransactionLimit, Balance)
            VALUES('%s', '%s', %f, %f)
            """.formatted(owner, accountNumber, transactionNumLimit, balance)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSavingAccount(String owner, String accountNumber, double withdrawalLimit, double balance) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(
                    """
                    INSERT INTO SavingsAccounts(Owner, AccountNumber, WithdrawalLimit, Balance)
                    VALUES('%s', '%s', %f, %f)
                    """.formatted(owner, accountNumber, withdrawalLimit, balance)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Utility methods
    * */
    private ResultSet getLoginData(String username, String password, String sql) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql.formatted(username, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public int getLastClientId() {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT seq FROM sqlite_sequence WHERE name = 'Clients'");
            return resultSet.getInt("seq");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
