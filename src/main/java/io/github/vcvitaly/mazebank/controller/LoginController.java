package io.github.vcvitaly.mazebank.controller;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label payeeAddressLbl;
    public ChoiceBox<String> accSelector;
    public TextField payeeAddressFld;
    public Label passwordLbl;
    public TextField passwordFld;
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
        Stage stage = (Stage) errorLbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        AccountType loginAccountType = Model.getInstance().getViewFactory().getLoginAccountType();
        if (loginAccountType == AccountType.CLIENT) {
            Model.getInstance().getViewFactory().showClientWindow();
        } else if (loginAccountType == AccountType.ADMIN) {
            Model.getInstance().getViewFactory().showAdminWindow();
        } else {
            throw new IllegalArgumentException("Unknown login account type: " + loginAccountType);
        }
    }
}
