package org.itstep.model.entity;


import java.util.List;

public class Nurse extends User {
    private String countOfPatients;
    private List<Patient> patients;

    private void resetSyncTime() {
        countOfPatients = String.valueOf(patients.size());
    }
}
