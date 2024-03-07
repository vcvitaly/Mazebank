package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.model.Client;
import io.github.vcvitaly.mazebank.model.Model;
import io.github.vcvitaly.mazebank.model.Transaction;
import io.github.vcvitaly.mazebank.view.TransactionCellFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        sendMoneyBtn.setOnAction(event -> onSendMoney());
        updateAccountSummary();
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

    private void onSendMoney() {
        final String receiver = payeeFld.getText();
        final double amount = Double.parseDouble(amountFld.getText());
        final String message = messageFld.getText();
        Model.getInstance().sendMoney(receiver, amount, message);
        clearFieldsAfterSendingMoney();
        Model.getInstance().setAllTransactions();
        Model.getInstance().setLatestTransactions();
        updateAccountSummary();
    }

    private void clearFieldsAfterSendingMoney() {
        payeeFld.setText("");
        amountFld.setText("");
        messageFld.setText("");
    }

    private void updateAccountSummary() {
        double income = 0;
        double expenses = 0;
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
        final String payeeAddress = Model.getInstance().getClient().getPayeeAddress().get();
        for (Transaction transaction : Model.getInstance().getAllTransactions()) {
            final double amount = transaction.getAmount().get();
            if (transaction.getSender().get().equals(payeeAddress)) {
                expenses += amount;
            } else {
                income += amount;
            }
        }
        incomeLbl.setText("+ $" + income);
        expensesLbl.setText("- $" + expenses);
    }
}
