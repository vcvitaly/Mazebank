package io.github.vcvitaly.mazebank.controller.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.vcvitaly.mazebank.model.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView inIcon;
    public FontAwesomeIconView outIcon;
    public Label transactionDateLbl;
    public Label senderLbl;
    public Label receiverLbl;
    public Label amountLbl;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
