package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.enumeration.FxmlView;
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
    }
    
    private void onClientCreation() {
        setAdminSelectedMenuItem(FxmlView.CLIENT_CREATION);
    }

    private void setAdminSelectedMenuItem(FxmlView fxmlView) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(fxmlView.toString());
    }
}
