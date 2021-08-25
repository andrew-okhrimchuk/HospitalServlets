package org.itstep.model.entity.enums;


import java.util.ArrayList;
import java.util.List;

public class Speciality {
    public static final String ALL = "ALL";
    public static final String LORE = "LORE";
    public static final String SPECIALITY = "SPECIALITY";
    public static final String GYNECOLOGIST = "GYNECOLOGIST";

    public static List<String> getAllSpeciality(){
        List <String> speciality = new ArrayList<>();
        speciality.add(ALL);
        speciality.add(LORE);
        speciality.add(SPECIALITY);
        speciality.add(GYNECOLOGIST);
        return speciality;
    }
}
