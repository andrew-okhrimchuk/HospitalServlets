package org.itstep.model.entity;

import java.util.*;

public class CoffeeMachine {
    private final int QUANTITY_BEVERAGE = 12;
    private final int MAX_QUANTITY_PORTION = 1000;
    private final long MAX_QUANTITY_WATER = 200_000;
    private final int MAX_QUANTITY_SUGAR = 10_000;
    private final int MAX_QUANTITY_CREAM = 2_000;
    private final int MAX_QUANTITY_SMALL_GLASSFUL = 1_000;
    private final int MAX_QUANTITY_MEDIUM_GLASSFUL = 1_000;
    private final int MAX_QUANTITY_BIG_GLASSFUL = 1_000;
    private List<Beverage> beverages;
    private Long water;
    private int sugar;
    private int cream;
    private int smallGlassful;
    private int mediumGlassful;
    private int bigGlassful;

    public CoffeeMachine() {
    }

    public CoffeeMachine(List<Beverage> beverages, Long water,
                         int sugar, int cream, int smallGlassful,
                         int mediumGlassful, int bigGlassful) {
        this.beverages = beverages;
        this.water = water;
        this.sugar = sugar;
        this.cream = cream;
        this.smallGlassful = smallGlassful;
        this.mediumGlassful = mediumGlassful;
        this.bigGlassful = bigGlassful;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public Long getWater() {
        return water;
    }

    public void setWater(Long water) {
        this.water = water;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getCream() {
        return cream;
    }

    public void setCream(int cream) {
        this.cream = cream;
    }

    public int getSmallGlassful() {
        return smallGlassful;
    }

    public void setSmallGlassful(int smallGlassful) {
        this.smallGlassful = smallGlassful;
    }

    public int getMediumGlassful() {
        return mediumGlassful;
    }

    public void setMediumGlassful(int mediumGlassful) {
        this.mediumGlassful = mediumGlassful;
    }

    public int getBigGlassful() {
        return bigGlassful;
    }

    public void setBigGlassful(int bigGlassful) {
        this.bigGlassful = bigGlassful;
    }
}
