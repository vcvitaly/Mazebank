package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.model.Client;
import io.github.vcvitaly.mazebank.model.Model;
import io.github.vcvitaly.mazebank.model.Transaction;
import io.github.vcvitaly.mazebank.view.TransactionCellFactory;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
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
    public Label dateLbl;
    public Label checkingBalanceLbl;
    public Label checkingAccNumLbl;
    public Label savingBalanceLbl;
    public Label savingsAccNumLbl;
    public Label incomeLbl;
    public Label expensesLbl;
    public ListView<Transaction> transactionListview;
    public TextField payeeFld;
    public TextField amountFld;
    public TextArea messageFld;
    public Button sendMoneyBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initLatestTransactions();
        transactionListview.setItems(Model.getInstance().getLatestTransactions());
        transactionListview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void bindData() {
        final Client client = Model.getInstance().getClient();
        usernameLbl.textProperty().bind(Bindings.concat("Hi, ").concat(client.getFirstName()));
        dateLbl.setText("Today, " + LocalDate.now());
        checkingBalanceLbl.textProperty().bind(client.getCheckingAccount().get().getBalance().asString());
        checkingAccNumLbl.textProperty().bind(client.getCheckingAccount().get().getAccountNumber());
        savingBalanceLbl.textProperty().bind(client.getSavingAccount().get().getBalance().asString());
        savingsAccNumLbl.textProperty().bind(client.getSavingAccount().get().getAccountNumber());
    }

    private void initLatestTransactions() {
        if (Model.getInstance().getLatestTransactions().isEmpty()) {
            Model.getInstance().setLatestTransactions();
        }
    }
}
