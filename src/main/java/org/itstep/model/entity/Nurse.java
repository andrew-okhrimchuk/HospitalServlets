package org.itstep.model.entity;


import java.util.List;

public class Nurse extends User {
    private String countOfPatients;
    private List<Patient> patients;

    private void resetSyncTime() {
        countOfPatients = String.valueOf(patients.size());
    }

    public String getCountOfPatients() {
        return countOfPatients;
    }

    public void setCountOfPatients(String countOfPatients) {
        this.countOfPatients = countOfPatients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "Nurse{" +
                "countOfPatients='" + countOfPatients + '\'' +
                ", patients=" + patients +
                '}';
    }
}
