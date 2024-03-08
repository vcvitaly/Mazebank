package io.github.vcvitaly.mazebank.controller.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.vcvitaly.mazebank.model.Model;
import io.github.vcvitaly.mazebank.model.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView inIcon;
    public FontAwesomeIconView outIcon;
    public Label transactionDateLbl;
    public Label senderLbl;
    public Label receiverLbl;
    public Button messageBtn;
    public Label amountLbl;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindData();
        updateTransactionIcons();
        messageBtn.setOnAction(event -> Model.getInstance().getViewFactory()
                .showTransactionMessageWindow(transaction.getSender().get(), transaction.getMessage().get())
        );
    }

    private void bindData() {
        transactionDateLbl.textProperty().bind(transaction.getDate().asString());
        senderLbl.textProperty().bind(transaction.getSender());
        receiverLbl.textProperty().bind(transaction.getReceiver());
        amountLbl.textProperty().bind(transaction.getAmount().asString());
    }

    private void updateTransactionIcons() {
        final String payeeAddress = Model.getInstance().getClient().getPayeeAddress().get();
        if (transaction.getSender().get().equals(payeeAddress)) {
            outIcon.setFill(Color.RED);
            inIcon.setFill(Color.LIGHTGREY);
        } else {
            inIcon.setFill(Color.GREEN);
            outIcon.setFill(Color.LIGHTGREY);
        }
    }
}
