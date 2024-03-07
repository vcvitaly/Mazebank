package io.github.vcvitaly.mazebank.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
