package io.github.vcvitaly.mazebank.controller.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCreationController implements Initializable {
    public TextField fNameFld;
    public TextField lNameFld;
    public TextField passwordFld;
    public CheckBox pAddressBox;
    public Label pAddressLbl;
    public CheckBox chAccBox;
    public TextField chAmountFld;
    public CheckBox svAccBox;
    public TextField svAmountFld;
    public Button createClientBtn;
    public Label errorLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
