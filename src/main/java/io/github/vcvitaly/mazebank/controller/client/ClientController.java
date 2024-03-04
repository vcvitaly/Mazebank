package io.github.vcvitaly.mazebank.controller.client;

import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane clientParent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (FxmlView.valueOf(newValue)) {
                case FxmlView.TRANSACTIONS -> clientParent.setCenter(
                        Model.getInstance().getViewFactory().getTransactionsView()
                );
                default -> clientParent.setCenter(
                        Model.getInstance().getViewFactory().getDashboardView()
                );
            }
        });
    }
}
