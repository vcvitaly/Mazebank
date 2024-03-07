package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.model.Model;
import io.github.vcvitaly.mazebank.model.Transaction;
import io.github.vcvitaly.mazebank.view.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public ListView<Transaction> transactionsListview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTransactions();
        transactionsListview.setItems(Model.getInstance().getAllTransactions());
        transactionsListview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initTransactions() {
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
    }
}
