package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.enumeration.AdminFxmlView;
import io.github.vcvitaly.mazebank.enumeration.ClientFxmlView;
import io.github.vcvitaly.mazebank.enumeration.MainFxmlView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@NoArgsConstructor
@Slf4j
public class ViewFactory {
    @Getter
    @Setter
    private AccountType loginAccountType = AccountType.CLIENT;
    // Client view fields
    @Getter
    private final ObjectProperty<ClientFxmlView> clientSelectedMenuItem = new SimpleObjectProperty<>();
    private final ViewHolder<AnchorPane> dashboardView = new ViewHolder<>(ClientFxmlView.DASHBOARD);
    private final ViewHolder<AnchorPane> transactionsView = new ViewHolder<>(ClientFxmlView.TRANSACTIONS);
    private final ViewHolder<AnchorPane> accountsView = new ViewHolder<>(ClientFxmlView.ACCOUNTS);
    // Admin views
    @Getter
    private final ObjectProperty<AdminFxmlView> adminSelectedMenuItem = new SimpleObjectProperty<>();
    private final ViewHolder<AnchorPane> clientCreationView = new ViewHolder<>(AdminFxmlView.CLIENT_CREATION);

    // Client view section
    public AnchorPane getDashboardView() {
        return getOrInitializeView(dashboardView);
    }

    public AnchorPane getTransactionsView() {
        return getOrInitializeView(transactionsView);
    }

    public AnchorPane getAccountsView() {
        return getOrInitializeView(accountsView);
    }

    // Admin view section
    public AnchorPane getClientCreationView() {
        return getOrInitializeView(clientCreationView);
    }

    private AnchorPane getOrInitializeView(ViewHolder<AnchorPane> view) {
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

    // Show window section
    public void showLoginWindow() {
        createStageAndShow(MainFxmlView.LOGIN);
    }

    public void showClientWindow() {
        createStageAndShow(MainFxmlView.CLIENT);
    }

    public void showAdminWindow() {
        createStageAndShow(MainFxmlView.ADMIN);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    private void createStageAndShow(MainFxmlView fxmlView) {
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
        log.info("Shown " + fxmlView);
    }
}
