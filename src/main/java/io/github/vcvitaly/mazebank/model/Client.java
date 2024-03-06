package io.github.vcvitaly.mazebank.model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(
            String firstName, String lastName, String payeeAddress,
            Account checkingAccount, Account savingAccount, LocalDate dateCreated) {
        this.firstName = new SimpleStringProperty(this, "firstName", firstName);
        this.lastName = new SimpleStringProperty(this, "lastName", lastName);
        this.payeeAddress = new SimpleStringProperty(this, "payeeAddress", payeeAddress);
        this.checkingAccount = new SimpleObjectProperty<>(this, "checkingAccount", checkingAccount);
        this.savingAccount = new SimpleObjectProperty<>(this, "savingAccount", savingAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "dateCreated", dateCreated);
    }
}
