package org.itstep.model.entity.enums;

public enum Glassful {
    SMALL (50),
    MEDIUM (150),
    BIG (250);

    private final int waterSize;

    Glassful(int waterSize) {
        this.waterSize = waterSize;
    }
}
