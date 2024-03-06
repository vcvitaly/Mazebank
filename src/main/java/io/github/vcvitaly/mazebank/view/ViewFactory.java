package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.enumeration.AdminFxmlView;
import io.github.vcvitaly.mazebank.enumeration.ClientFxmlView;
import io.github.vcvitaly.mazebank.enumeration.ImageResource;
import io.github.vcvitaly.mazebank.enumeration.MainFxmlView;
import io.github.vcvitaly.mazebank.util.FxmlLoaderUtil;
import io.github.vcvitaly.mazebank.util.ResourceUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private final ViewHolder<AnchorPane> clientsView = new ViewHolder<>(AdminFxmlView.CLIENTS);
    private final ViewHolder<AnchorPane> depositView = new ViewHolder<>(AdminFxmlView.DEPOSIT);

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

    public AnchorPane getClientsView() {
        return getOrInitializeView(clientsView);
    }

    public AnchorPane getDepositView() {
        return getOrInitializeView(depositView);
    }

    private AnchorPane getOrInitializeView(ViewHolder<AnchorPane> view) {
        if (view.view().get() == null) {
            synchronized (view.lock()) {
                if (view.view().get() == null) {
                    try {
                        view.view().set(FxmlLoaderUtil.createFxmlLoader(view.fxmlView()).load());
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
        FXMLLoader loader = FxmlLoaderUtil.createFxmlLoader(fxmlView);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(
                new Image(
                        ResourceUtil.getResource(ImageResource.APP_ICON.getResourcePath()).toString()
                )
        );
        stage.setResizable(false);
        stage.setTitle("Maze Bank");
        stage.show();
        log.info("Shown " + fxmlView);
    }
}
