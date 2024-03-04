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
    private final ViewHolder<AnchorPane> dashboardView = new ViewHolder<>(FxmlView.DASHBOARD);
    private final ViewHolder<AnchorPane> transactionsView = new ViewHolder<>(FxmlView.TRANSACTIONS);
    private final ViewHolder<AnchorPane> accountsView = new ViewHolder<>(FxmlView.ACCOUNTS);

    public AnchorPane getDashboardView() {
        return getView(dashboardView);
    }

    public AnchorPane getTransactionsView() {
        return getView(transactionsView);
    }

    public AnchorPane getAccountsView() {
        return getView(accountsView);
    }

    private AnchorPane getView(ViewHolder<AnchorPane> view) {
        if (view.view().get() == null) {
            synchronized (view.lock()) {
                if (view.view().get() == null) {
                    try {
                        view.view().set(new FXMLLoader(getClass().getResource(view.fxmlView().getResourcePath())).load());
                        log.info("Created a %s view".formatted(view.fxmlView()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return view.view().get();
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
