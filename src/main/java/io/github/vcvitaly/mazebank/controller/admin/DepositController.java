package io.github.vcvitaly.mazebank.controller.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField searchByPayeeFld;
    public Button searchBtn;
    public ListView resultListview;
    public TextField depositAmountFld;
    public Button depositBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
