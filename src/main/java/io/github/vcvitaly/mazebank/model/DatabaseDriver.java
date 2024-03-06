package io.github.vcvitaly.mazebank.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    /*
    * Admin section
    * */

    /*
    * Utility methods
    * */
}
