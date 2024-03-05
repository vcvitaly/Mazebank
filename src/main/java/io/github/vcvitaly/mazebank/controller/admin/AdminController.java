package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.controller.AccountTypeHelper;
import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable, AccountTypeHelper {

    public BorderPane adminParent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case CLIENT_CREATION -> adminParent.setCenter(Model.getInstance().getViewFactory().getClientCreationView());
                case CLIENTS -> adminParent.setCenter(Model.getInstance().getViewFactory().getClientsView());
                case DEPOSIT -> adminParent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                default -> throw constructException(newValue);
            }
        });
    }

    @Override
    public AccountType getMainControllerType() {
        return AccountType.ADMIN;
    }
}
