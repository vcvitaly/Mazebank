package io.github.vcvitaly.mazebank.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImageResource implements Resource {
    APP_ICON("/images/icon.png");

    private final String resourcePath;

    @Override
    public String getResourcePath() {
        return resourcePath;
    }
}
