package io.github.vcvitaly.mazebank.controller.client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label chAccNumber;
    public Label transactionLimit;
    public Label chAccDateCreated;
    public Label chAccBalance;
    public Label svAccNumber;
    public Label withdrawalLimit;
    public Label svAccDateCreated;
    public Label svAccBalance;
    public TextField moveToSvAmount;
    public Button moveToSvBtn;
    public TextField moveToChAmount;
    public Button moveToChBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
