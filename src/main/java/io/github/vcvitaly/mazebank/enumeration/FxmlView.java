package io.github.vcvitaly.mazebank.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FxmlView {
    LOGIN("/fxml/login.fxml"),
    ADMIN("/fxml/admin/admin.fxml"),
    CLIENT("/fxml/client/client.fxml"),
    CLIENT_MENU("/fxml/client/clientMenu.fxml"),
    DASHBOARD("/fxml/client/dashboard.fxml"),
    TRANSACTIONS("/fxml/client/transactions.fxml"),
    ACCOUNTS("/fxml/client/accounts.fxml");

    private final String resourcePath;
}
