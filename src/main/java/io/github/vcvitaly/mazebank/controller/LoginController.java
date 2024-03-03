package io.github.vcvitaly.mazebank.controller;

import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label payeeAddressLbl;
    public TextField payeeAddressFld;
    public Label passwordLbl;
    public TextField passwordFld;
    public Button loginBtn;
    public Label errorLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(event -> Model.getInstance().getViewFactory().showClientWindow());
    }
}
