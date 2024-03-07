package io.github.vcvitaly.mazebank.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public abstract class Account {
    private final StringProperty owner;
    private final StringProperty accountNumber;
    private DoubleProperty balance;

    public Account(String owner, String accountNumber, double balance) {
        this.owner = new SimpleStringProperty(this, "owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "accountNumber", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "balance", balance);
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }
}
