package org.itstep.model.service;

import org.itstep.model.entity.Beverage;
import org.itstep.model.entity.CoffeeMachine;
import org.itstep.model.entity.enums.*;
import java.util.Arrays;
import java.util.List;

public class InitCoffeeMachine {
    public CoffeeMachine init (){
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.setBeverages(initBeverage());
        coffeeMachine.setWater(75_000L);
        coffeeMachine.setSugar(4_500);
        coffeeMachine.setCream(750);
        coffeeMachine.setSmallGlassful(524);
        coffeeMachine.setMediumGlassful(368);
        coffeeMachine.setBigGlassful(639);

        return coffeeMachine;
    }

    private List<Beverage> initBeverage() {
        List<Beverage> beverages = Arrays.asList(
           new Beverage("Americana", FirstBasicIngredient.AMERICANA,
                   null, 50,
                   Glassful.SMALL, Sugar.MEDIUM, 2700),
           new Beverage ("DoubleAmericana",
                   FirstBasicIngredient.AMERICANA,
                   SecondBasicIngredient.AMERICANA, 50,
                   Glassful.SMALL, Sugar.MEDIUM, 3400),
           new Beverage ("Arabic", FirstBasicIngredient.ARABIC,
                   null, 50,
                   Glassful.SMALL, Sugar.MEDIUM, 2500),
           new Beverage ("Tea", FirstBasicIngredient.GREY_TEA, null, 200, Glassful.BIG,
                        Sugar.MEDIUM, 1200)
        );
        return beverages;
    }
}
