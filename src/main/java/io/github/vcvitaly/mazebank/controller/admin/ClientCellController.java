package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.model.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientCellController implements Initializable {
    public Label firstNameLbl;
    public Label lastNameLbl;
    public Label payeeAddressLbl;
    public Label chAccountLbl;
    public Label svAccountLbl;
    public Label dateLbl;
    public Button deleteBtn;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameLbl.textProperty().bind(client.getFirstName());
        lastNameLbl.textProperty().bind(client.getLastName());
        payeeAddressLbl.textProperty().bind(client.getPayeeAddress());
        chAccountLbl.textProperty().bind(client.getCheckingAccount().get().getAccountNumber());
        svAccountLbl.textProperty().bind(client.getSavingAccount().get().getAccountNumber());
        dateLbl.textProperty().bind(client.getDateCreated().asString());
    }
}
