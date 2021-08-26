package org.itstep.model.entity.enums;


public enum Role  {
    ADMIN, DOCTOR, NURSE, PATIENT;

    public String getAuthority() {
        return name();
    }

}
