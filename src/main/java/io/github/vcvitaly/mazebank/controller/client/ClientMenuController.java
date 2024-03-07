package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.enumeration.ClientFxmlView;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

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
        logoutBtn.setOnAction(event -> onLogout());
    }

    private void onDashboard() {
        setClientSelectedMenuItem(ClientFxmlView.DASHBOARD);
    }

    private void onTransactions() {
        setClientSelectedMenuItem(ClientFxmlView.TRANSACTIONS);
    }

    private void onAccounts() {
        setClientSelectedMenuItem(ClientFxmlView.ACCOUNTS);
    }

    private void onLogout() {
        Stage stage = (Stage) dashboardBtn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    private void setClientSelectedMenuItem(ClientFxmlView fxmlView) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(fxmlView);
    }
}
