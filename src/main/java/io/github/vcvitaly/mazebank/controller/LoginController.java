package io.github.vcvitaly.mazebank.controller;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiPredicate;
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
        accSelector.valueProperty().addListener(observable -> setAccSelector());
        loginBtn.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        final AccountType loginAccountType = Model.getInstance().getViewFactory().getLoginAccountType();
        final String payeeAddress = payeeAddressFld.getText();
        final String password = passwordFld.getText();
        if (payeeAddress.isBlank() || password.isBlank()) {
            errorLbl.setText("Both payee address and password should not be empty");
        } else {
            if (loginAccountType == AccountType.CLIENT) {
                handleSuccessfulLogin(
                        payeeAddress,
                        password,
                        (username, pass) -> Model.getInstance().evaluateClientCredentials(username, pass),
                        () -> Model.getInstance().getViewFactory().showClientWindow()
                );
            } else if (loginAccountType == AccountType.ADMIN) {
                handleSuccessfulLogin(
                        payeeAddress,
                        password,
                        (username, pass) -> Model.getInstance().evaluateAdminCredentials(username, pass),
                        () -> Model.getInstance().getViewFactory().showAdminWindow()
                );
            } else {
                throw new IllegalArgumentException("Unknown login account type: " + loginAccountType);
            }
        }
    }

    private void handleSuccessfulLogin(
            String payeeAddress,
            String password,
            BiPredicate<String, String> credentialEvaluator,
            Runnable windowRunnable
            ) {
        final Stage loginStage = (Stage) errorLbl.getScene().getWindow();
        if (credentialEvaluator.test(payeeAddress, password)) {
            windowRunnable.run();
            Model.getInstance().getViewFactory().closeStage(loginStage);
        } else {
            payeeAddressFld.setText("");
            passwordFld.setText("");
            errorLbl.setText("Unknown login credentials");
        }
    }

    private void setAccSelector() {
        final AccountType accountType = AccountType.ofValue(accSelector.getValue());
        Model.getInstance().getViewFactory().setLoginAccountType(accountType);
        payeeAddressLbl.setText("%s:".formatted(accountType.getLabel()));
    }
}
