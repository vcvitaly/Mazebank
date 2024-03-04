package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboardBtn;
    public Button transactionBtn;
    public Button accountsBtn;
    public Button profileBtn;
    public Button logoutBtn;
    public Button reportBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboardBtn.setOnAction(event -> onDashboard());
        transactionBtn.setOnAction(event -> onTransactions());
        accountsBtn.setOnAction(event -> onAccounts());
    }

    private void onDashboard() {
        setClientSelectedMenuItem(FxmlView.DASHBOARD);
    }

    private void onTransactions() {
        setClientSelectedMenuItem(FxmlView.TRANSACTIONS);
    }

    private void onAccounts() {
        setClientSelectedMenuItem(FxmlView.ACCOUNTS);
    }

    private void setClientSelectedMenuItem(FxmlView fxmlView) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(fxmlView.toString());
    }
}
