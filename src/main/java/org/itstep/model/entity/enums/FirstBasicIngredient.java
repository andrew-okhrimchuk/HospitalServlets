package org.itstep.model.entity.enums;

public enum FirstBasicIngredient {
    AMERICANA (1000),
    ARABIC (700),
    GREY_TEA (250);

    private final int quantity;

    FirstBasicIngredient(int quantity) {
        this.quantity = quantity;
    }

}
