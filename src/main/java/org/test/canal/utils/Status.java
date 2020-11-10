package org.test.canal.utils;

import java.util.Arrays;
import java.util.Optional;

public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive");
    private final String status;
    Status(String status) {
        this.status=status;
    }
    public static Optional<Status> fromText(String text) {
        return Arrays.stream(values())
                .filter(bl -> bl.status.equalsIgnoreCase(text))
                .findFirst();
    }
}
