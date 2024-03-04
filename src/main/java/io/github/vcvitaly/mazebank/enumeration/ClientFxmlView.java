package io.github.vcvitaly.mazebank.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClientFxmlView implements FxmlView {
    DASHBOARD("/fxml/client/dashboard.fxml"),
    TRANSACTIONS("/fxml/client/transactions.fxml"),
    ACCOUNTS("/fxml/client/accounts.fxml");

    private final String resourcePath;

    @Override
    public String getResourcePath() {
        return resourcePath;
    }
}
