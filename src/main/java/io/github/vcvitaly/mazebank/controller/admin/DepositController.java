package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.model.Client;
import io.github.vcvitaly.mazebank.model.Model;
import io.github.vcvitaly.mazebank.view.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField searchByPayeeFld;
    public Button searchBtn;
    public ListView<Client> resultListview;
    public TextField depositAmountFld;
    public Button depositBtn;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBtn.setOnAction(event -> onClientSearch());
        depositBtn.setOnAction(event -> onDeposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResult = Model.getInstance().searchClient(searchByPayeeFld.getText());
        resultListview.setItems(searchResult);
        resultListview.setCellFactory(e -> new ClientCellFactory());
        client = searchResult.getFirst();
    }

    private void onDeposit() {
        if (depositAmountFld.getText() != null) {
            double amount = Double.parseDouble(depositAmountFld.getText());
            double newBalance = amount + client.getSavingAccount().get().getBalance().get();
            Model.getInstance().getDatabaseDriver().depositSavings(client.getPayeeAddress().get(), newBalance);
        }
        emptyFields();
    }

    private void emptyFields() {
        searchByPayeeFld.setText("");
        depositAmountFld.setText("");
    }
}
