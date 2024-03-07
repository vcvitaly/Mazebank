package io.github.vcvitaly.mazebank.model;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.exception.MalformedClientDataException;
import io.github.vcvitaly.mazebank.view.ViewFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Model {
    @Getter
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    // Client data section
    @Getter
    private final Client client;

    private Model() {
        viewFactory = new ViewFactory();
        databaseDriver = new DatabaseDriver();
        // Client data section
        client = new Client("", "", "", null, null, null);
        // Admin data section
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
                final String dateStr = resultSet.getString("Date");
                final String[] dateParts = dateStr.split("-");
                if (dateParts.length != 3) {
                    throw new MalformedClientDataException("Invalid date format: " + dateStr);
                }
                final LocalDate date = LocalDate.of(
                        Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]),
                        Integer.parseInt(dateParts[2])
                );
                client.getDateCreated().set(date);
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

    public static Model getInstance() {
        return ModelHolder.model;
    }

    private static class ModelHolder {
        private static final Model model = new Model();
    }
}
