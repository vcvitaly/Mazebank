package io.github.vcvitaly.mazebank.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum AccountType {
    CLIENT("Client", "Payee address"),
    ADMIN("Admin", "Username");

    @Getter
    private final String value;
    @Getter
    private final String label;

    public static AccountType ofValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No account type of value: " + value));
    }
}
