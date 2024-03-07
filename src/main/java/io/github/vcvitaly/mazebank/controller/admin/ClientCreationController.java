package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.enumeration.ClientAccountType;
import io.github.vcvitaly.mazebank.model.Model;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ClientCreationController implements Initializable {
    private static final int DEFAULT_TRANSACTION_NUM_LIMIT = 10;
    private static final int DEFAULT_WITHDRAWAL_LIMIT = 2000;
    public TextField fNameFld;
    public TextField lNameFld;
    public PasswordField passwordFld;
    public CheckBox pAddressBox;
    public Label pAddressLbl;
    public CheckBox chAccBox;
    public TextField chAmountFld;
    public CheckBox svAccBox;
    public TextField svAmountFld;
    public Button createClientBtn;
    public Label errorLbl;

    private final Random random = new Random();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createClientBtn.setOnAction(event -> createClient());
        pAddressBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                onCreatePayeeAddress();
            }
        });
    }

    private void createClient() {
        if (chAccBox.isSelected()) {
            createAccount(ClientAccountType.CHECKING);
        }
        if (svAccBox.isSelected()) {
            createAccount(ClientAccountType.SAVING);
        }
        final String firstName = fNameFld.getText();
        final String lastName = lNameFld.getText();
        final String password = passwordFld.getText();
        final String payeeAddress = pAddressLbl.getText();
        Model.getInstance().getDatabaseDriver().createClient(firstName, lastName, payeeAddress, password, LocalDate.now());
        errorLbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
        errorLbl.setText("A client was created successfully!");
        emptyFields();
    }

    private void createAccount(ClientAccountType clientAccountType) {
        final String firstSection = "3201";
        final String lastSection = Integer.toString(random.nextInt(9_999));
        final String accountNumber = "%s %s".formatted(firstSection, lastSection);
        final String payeeAddress = pAddressLbl.getText();
        if (clientAccountType == ClientAccountType.CHECKING) {
            double balance = Double.parseDouble(chAmountFld.getText());
            Model.getInstance().getDatabaseDriver()
                    .createCheckingAccount(payeeAddress, accountNumber, DEFAULT_TRANSACTION_NUM_LIMIT, balance);
        } else if (clientAccountType == ClientAccountType.SAVING) {
            double balance = Double.parseDouble(svAmountFld.getText());
            Model.getInstance().getDatabaseDriver()
                    .createSavingAccount(payeeAddress, accountNumber, DEFAULT_WITHDRAWAL_LIMIT, balance);
        } else {
            throw new IllegalArgumentException("Unknown client account type: " + clientAccountType);
        }
    }

    private void onCreatePayeeAddress() {
        if (fNameFld.getText() != null && lNameFld.getText() != null) {
            pAddressLbl.setText(createPayeeAddress());
        }
    }

    private String createPayeeAddress() {
        final int id = Model.getInstance().getDatabaseDriver().getLastClientId() + 1;
        final char fChar = Character.toLowerCase(fNameFld.getText().charAt(0));
        return "@" + fChar + lNameFld.getText() + id;
    }

    private void emptyFields() {
        fNameFld.setText("");
        lNameFld.setText("");
        passwordFld.setText("");
        pAddressBox.setSelected(false);
        pAddressLbl.setText("");
        chAccBox.setSelected(false);
        chAmountFld.setText("");
        svAccBox.setSelected(false);
        svAmountFld.setText("");
    }
}
