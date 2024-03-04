package io.github.vcvitaly.mazebank.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MainFxmlView implements FxmlView {
    LOGIN("/fxml/login.fxml"),
    ADMIN("/fxml/admin/admin.fxml"),
    ADMIN_MENU("/fxml/client/adminMenu.fxml"),
    CLIENT("/fxml/client/client.fxml"),
    CLIENT_MENU("/fxml/client/clientMenu.fxml");

    private final String resourcePath;

    @Override
    public String getResourcePath() {
        return resourcePath;
    }
}
