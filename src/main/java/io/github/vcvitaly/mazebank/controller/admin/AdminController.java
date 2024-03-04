package io.github.vcvitaly.mazebank.controller.admin;

import io.github.vcvitaly.mazebank.controller.MainController;
import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import io.github.vcvitaly.mazebank.enumeration.MainControllerType;
import io.github.vcvitaly.mazebank.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable, MainController {

    public BorderPane adminParent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            FxmlView fxmlView = FxmlView.valueOf(newValue);
            switch (fxmlView) {
                case CLIENT_CREATION -> adminParent.setCenter(Model.getInstance().getViewFactory().getClientCreationView());
                default -> throw constructException(fxmlView);
            }
        });
    }

    @Override
    public MainControllerType getMainControllerType() {
        return MainControllerType.ADMIN;
    }
}
