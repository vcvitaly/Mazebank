package io.github.vcvitaly.mazebank.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CellFxmlView implements FxmlView {
    TRANSACTION_CELL("/fxml/client/transactionCell.fxml"),
    CLIENT_CELL("/fxml/admin/clientCell.fxml");

    private final String resourcePath;

    @Override
    public String getResourcePath() {
        return resourcePath;
    }
}
