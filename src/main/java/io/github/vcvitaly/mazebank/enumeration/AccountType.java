package io.github.vcvitaly.mazebank.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum AccountType {
    CLIENT("Client"),
    ADMIN("Admin");

    @Getter
    private final String value;

    public static AccountType ofValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No account type of value: " + value));
    }
}
