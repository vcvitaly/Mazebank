package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@NoArgsConstructor
@Slf4j
public class ViewFactory {
    // Client views
    @Getter
    private final StringProperty clientSelectedMenuItem = new SimpleStringProperty("");
    private volatile AnchorPane dashboardView;
    private volatile AnchorPane transactionsView;

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            synchronized (this) {
                if (dashboardView == null) {
                    try {
                        dashboardView = new FXMLLoader(getClass().getResource(FxmlView.DASHBOARD.getResourcePath())).load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dashboardView;
    }

    public AnchorPane getTransactionsView() {
        if (transactionsView == null) {
            synchronized (this) {
                if (transactionsView == null) {
                    try {
                        transactionsView = new FXMLLoader(getClass().getResource(FxmlView.TRANSACTIONS.getResourcePath())).load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return transactionsView;
    }

    public void showLoginWindow() {
        createStageAndShow(FxmlView.LOGIN);
    }

    public void showClientWindow() {
        createStageAndShow(FxmlView.CLIENT);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    private void createStageAndShow(FxmlView fxmlView) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlView.getResourcePath()));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze Bank");
        stage.show();
    }
}
