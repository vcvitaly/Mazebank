package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.controller.AccountTypeHelper;
import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable, AccountTypeHelper {

    public BorderPane clientParent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case TRANSACTIONS -> clientParent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                case ACCOUNTS -> clientParent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                case DASHBOARD -> clientParent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                default -> throw constructException(newValue);
            }
        });
    }

    @Override
    public AccountType getMainControllerType() {
        return AccountType.CLIENT;
    }
}
