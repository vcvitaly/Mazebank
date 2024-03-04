package io.github.vcvitaly.mazebank.view;

import io.github.vcvitaly.mazebank.enumeration.FxmlView;

import java.util.concurrent.atomic.AtomicReference;

public record ViewHolder<T>(AtomicReference<T> view, FxmlView fxmlView, Object lock) {
    public ViewHolder(FxmlView fxmlView) {
        this(new AtomicReference<>(), fxmlView, new Object());
    }
}
