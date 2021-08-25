package org.itstep.model.entity.enums;

public enum Sugar {
    NONE (0),
    SMALL (1),
    MEDIUM (2),
    BIG (3),
    SUPER_BIG(4);

    private int portion;

    Sugar(int portion) {
        this.portion = portion;
    }
}
