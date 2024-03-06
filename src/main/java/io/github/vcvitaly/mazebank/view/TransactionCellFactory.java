package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.controller.client.TransactionCellController;
import io.github.vcvitaly.mazebank.enumeration.CellFxmlView;
import io.github.vcvitaly.mazebank.model.Transaction;
import io.github.vcvitaly.mazebank.util.FxmlLoaderUtil;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TransactionCellFactory extends ListCell<Transaction> {

    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        setText(null);
        if (empty) {
            setGraphic(null);
        } else {
            final FXMLLoader loader = FxmlLoaderUtil.createFxmlLoader(CellFxmlView.TRANSACTION_CELL);
            final TransactionCellController controller = new TransactionCellController(transaction);
            loader.setController(controller);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
