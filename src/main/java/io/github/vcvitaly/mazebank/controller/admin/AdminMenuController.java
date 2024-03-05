package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.enumeration.AdminFxmlView;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button clientCreationBtn;
    public Button clientsBtn;
    public Button depositBtn;
    public Button logoutBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
    }
    
    private void addListeners() {
        clientCreationBtn.setOnAction(event -> onClientCreation());
        clientsBtn.setOnAction(event -> onClients());
        depositBtn.setOnAction(event -> onDeposit());
    }
    
    private void onClientCreation() {
        setAdminSelectedMenuItem(AdminFxmlView.CLIENT_CREATION);
    }

    private void onClients() {
        setAdminSelectedMenuItem(AdminFxmlView.CLIENTS);
    }

    private void onDeposit() {
        setAdminSelectedMenuItem(AdminFxmlView.DEPOSIT);
    }

    private void setAdminSelectedMenuItem(AdminFxmlView fxmlView) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(fxmlView);
    }
}
