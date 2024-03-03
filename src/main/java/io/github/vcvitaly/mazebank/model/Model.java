package io.github.vcvitaly.mazebank.model;

import io.github.vcvitaly.mazebank.view.ViewFactory;
import lombok.Getter;

public class Model {
    @Getter
    private final ViewFactory viewFactory;

    private Model() {
        viewFactory = new ViewFactory();
    }

    public static Model getInstance() {
        return ModelHolder.model;
    }

    private static class ModelHolder {
        private static final Model model = new Model();
    }
}
