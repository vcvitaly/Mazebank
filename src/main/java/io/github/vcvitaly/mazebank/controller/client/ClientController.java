package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.controller.MainController;
import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import io.github.vcvitaly.mazebank.enumeration.MainControllerType;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable, MainController {

    public BorderPane clientParent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            FxmlView fxmlView = FxmlView.valueOf(newValue);
            switch (fxmlView) {
                case TRANSACTIONS -> clientParent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                case ACCOUNTS -> clientParent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                case DASHBOARD -> clientParent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                default -> throw constructException(fxmlView);
            }
        });
    }

    @Override
    public MainControllerType getMainControllerType() {
        return MainControllerType.CLIENT;
    }
}
