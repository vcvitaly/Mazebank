package io.github.vcvitaly.mazebank.controller.client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label usernameLbl;
    public Label checkingBalanceLbl;
    public Label checkingAccNumLbl;
    public Label savingsBalanceLbl;
    public Label savingsAccNumLbl;
    public Label incomeLbl;
    public Label expensesLbl;
    public ListView transactionListview;
    public TextField payeeFld;
    public TextField amountFld;
    public TextArea messageFld;
    public Button sendMoneyBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
