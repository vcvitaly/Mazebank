package io.github.vcvitaly.mazebank.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

@Getter
public class CheckingAccount extends Account {
    // The number of transactions a client is allowed to do per day
    private final IntegerProperty transactionNumLimit;

    public CheckingAccount (String owner, String accountNumber, double balance, int transactionNumLimit) {
        super(owner, accountNumber, balance);
        this.transactionNumLimit = new SimpleIntegerProperty(this, "transactionNumLimit", transactionNumLimit);
    }
}
