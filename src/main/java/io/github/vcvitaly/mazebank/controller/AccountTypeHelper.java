package io.github.vcvitaly.mazebank.controller;

import io.github.vcvitaly.mazebank.enumeration.AccountType;
import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import io.github.vcvitaly.mazebank.util.Constants;

public interface AccountTypeHelper {

    default IllegalArgumentException constructException(FxmlView fxmlView) {
        return new IllegalArgumentException(
                Constants.UNDEFINED_VIEW_TYPE_MSG.formatted(getMainControllerType().toString().toLowerCase(), fxmlView)
        );
    }

    AccountType getMainControllerType();
}
