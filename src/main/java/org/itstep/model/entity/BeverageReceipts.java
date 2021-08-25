package org.itstep.model.entity;

import org.itstep.model.entity.enums.FirstBasicIngredient;
import org.itstep.model.entity.enums.Glassful;
import org.itstep.model.entity.enums.SecondBasicIngredient;
import org.itstep.model.entity.enums.Sugar;

public enum BeverageReceipts {
    AMERICANA (FirstBasicIngredient.AMERICANA, null, 50, Glassful.SMALL,
            Sugar.MEDIUM, 2700),
    DOUBLE_AMERICANA (FirstBasicIngredient.AMERICANA, SecondBasicIngredient.AMERICANA,
            50, Glassful.SMALL, Sugar.MEDIUM, 3400),
    ARABIC (FirstBasicIngredient.ARABIC, null, 50, Glassful.SMALL,
            Sugar.MEDIUM, 2500),
    TEA (FirstBasicIngredient.GREY_TEA, null, 200, Glassful.BIG,
            Sugar.MEDIUM, 1200);

    BeverageReceipts(FirstBasicIngredient firstBasicIngredient,
                     SecondBasicIngredient secondBasicIngredient,
                     int water,
                     Glassful glassful,
                     Sugar sugar,
                     int cost) {
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
