package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.controller.admin.ClientCellController;
import io.github.vcvitaly.mazebank.enumeration.CellFxmlView;
import io.github.vcvitaly.mazebank.model.Client;
import io.github.vcvitaly.mazebank.util.FxmlLoaderUtil;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientCellFactory extends ListCell<Client> {

    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        setText(null);
        if (empty) {
            setGraphic(null);
        } else {
            final FXMLLoader loader = FxmlLoaderUtil.createFxmlLoader(CellFxmlView.CLIENT_CELL);
            final ClientCellController controller = new ClientCellController(client);
            loader.setController(controller);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
