package org.itstep.model.entity;

import org.itstep.model.entity.enums.FirstBasicIngredient;
import org.itstep.model.entity.enums.Glassful;
import org.itstep.model.entity.enums.SecondBasicIngredient;
import org.itstep.model.entity.enums.Sugar;

public class Beverage {
    private String name;
    private FirstBasicIngredient firstBasicIngredient;
    private SecondBasicIngredient secondBasicIngredient;
    private int water;
    private Glassful glassful;
    private Sugar sugar;
    private int cost;

    public Beverage(String name,
                    FirstBasicIngredient firstBasicIngredient,
                    SecondBasicIngredient secondBasicIngredient,
                    int water,
                    Glassful glassful,
                    Sugar sugar,
                    int cost) {
        this.name = name;
        this.firstBasicIngredient = firstBasicIngredient;
        this.secondBasicIngredient = secondBasicIngredient;
        this.water = water;
        this.glassful = glassful;
        this.sugar = sugar;
        this.cost = cost;
    }
}
