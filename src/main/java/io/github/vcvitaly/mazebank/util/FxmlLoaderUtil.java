package io.github.vcvitaly.mazebank.util;

import io.github.vcvitaly.mazebank.enumeration.FxmlView;
import javafx.fxml.FXMLLoader;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FxmlLoaderUtil {

    public static FXMLLoader createFxmlLoader(FxmlView fxmlView) {
        return new FXMLLoader(FxmlLoaderUtil.class.getResource(fxmlView.getResourcePath()));
    }
}
