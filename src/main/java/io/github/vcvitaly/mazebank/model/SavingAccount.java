package io.github.vcvitaly.mazebank.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Builder;

public class SavingAccount extends Account {
    // The withdrawal limit per day
    private final DoubleProperty withdrawalLimit;

    @Builder
    public SavingAccount(String owner, String accountNumber, double balance, double withdrawalLimit) {
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "withdrawalLimit", withdrawalLimit);
    }
}
