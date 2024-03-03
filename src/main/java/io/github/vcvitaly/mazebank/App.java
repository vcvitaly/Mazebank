package io.github.vcvitaly.mazebank;

import io.github.vcvitaly.mazebank.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
