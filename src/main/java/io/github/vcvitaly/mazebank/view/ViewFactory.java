package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.enumeration.FxmlResource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@NoArgsConstructor
@Slf4j
public class ViewFactory {
    // Client views
    private volatile AnchorPane dashboardView;

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            synchronized (this) {
                if (dashboardView == null) {
                    try {
                        dashboardView = new FXMLLoader(getClass().getResource(FxmlResource.DASHBOARD.getResourcePath())).load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dashboardView;
    }

    public void showLoginWindow() {
        createStageAndShow(FxmlResource.LOGIN);
    }

    public void showClientWindow() {
        createStageAndShow(FxmlResource.CLIENT);
    }

    private void createStageAndShow(FxmlResource fxmlResource) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource.getResourcePath()));
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
