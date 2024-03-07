package io.github.vcvitaly.mazebank.controller;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    public Label payeeAddressLbl;
    public ChoiceBox<String> accSelector;
    public TextField payeeAddressFld;
    public Label passwordLbl;
    public PasswordField passwordFld;
    public Button loginBtn;
    public Label errorLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accSelector.setItems(FXCollections.observableArrayList(
                AccountType.CLIENT.getValue(), AccountType.ADMIN.getValue()
        ));
        accSelector.setValue(Model.getInstance().getViewFactory().getLoginAccountType().getValue());
        accSelector.valueProperty().addListener(
                observable -> Model.getInstance().getViewFactory().setLoginAccountType(
                        AccountType.ofValue(accSelector.getValue())
                )
        );
        loginBtn.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage loginStage = (Stage) errorLbl.getScene().getWindow();
        AccountType loginAccountType = Model.getInstance().getViewFactory().getLoginAccountType();
        if (loginAccountType == AccountType.CLIENT) {
            final String payeeAddress = payeeAddressFld.getText();
            final String password = passwordFld.getText();
            if (payeeAddress.isBlank() || password.isBlank()) {
                errorLbl.setText("Both payee address and password should not be empty");
            } else {
                Model.getInstance().evaluateClientCredentials(payeeAddress, password);
                if (Model.getInstance().isClientLoginSucceeded()) {
                    Model.getInstance().getViewFactory().showClientWindow();
                    Model.getInstance().getViewFactory().closeStage(loginStage);
                } else {
                    payeeAddressFld.setText("");
                    passwordFld.setText("");
                    errorLbl.setText("Unknown client credentials");
                }
            }
        } else if (loginAccountType == AccountType.ADMIN) {
            Model.getInstance().getViewFactory().showAdminWindow();
        } else {
            throw new IllegalArgumentException("Unknown login account type: " + loginAccountType);
        }
    }
}
