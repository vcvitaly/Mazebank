package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.model.Client;
import io.github.vcvitaly.mazebank.model.Model;
import io.github.vcvitaly.mazebank.view.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminClientViewController implements Initializable {
    public ListView<Client> clientsListview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        clientsListview.setItems(Model.getInstance().getClients());
        clientsListview.setCellFactory(e -> new ClientCellFactory());
    }

    private void initData() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }
}
