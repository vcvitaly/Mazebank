package io.github.vcvitaly.mazebank.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AdminFxmlView implements FxmlView {
    CLIENT_CREATION("/fxml/admin/clientCreation.fxml"),
    CLIENTS("/fxml/admin/clients.fxml"),
    DEPOSIT("/fxml/admin/deposit.fxml");

    private final String resourcePath;

    @Override
    public String getResourcePath() {
        return resourcePath;
    }
}
