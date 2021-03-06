package org.itstep.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class HospitalList {
    private Long id;
    private String primaryDiagnosis;
    private String finalDiagnosis;
    private String medicine;
    private String operations;
    private String doctorName;

    private Patient patientId;
    private LocalDateTime dateCreate;
    private LocalDateTime dateDischarge;
    private List<MedicationLog> hospitallistid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryDiagnosis() {
        return primaryDiagnosis;
    }

    public void setPrimaryDiagnosis(String primaryDiagnosis) {
        this.primaryDiagnosis = primaryDiagnosis;
    }

    public String getFinalDiagnosis() {
        return finalDiagnosis;
    }

    public void setFinalDiagnosis(String finalDiagnosis) {
        this.finalDiagnosis = finalDiagnosis;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateDischarge() {
        return dateDischarge;
    }

    public void setDateDischarge(LocalDateTime dateDischarge) {
        this.dateDischarge = dateDischarge;
    }

    public List<MedicationLog> getHospitallistid() {
        return hospitallistid;
    }

    public void setHospitallistid(List<MedicationLog> hospitallistid) {
        this.hospitallistid = hospitallistid;
    }

    @Override
    public String toString() {
        return "HospitalList{" +
                "hospitalListId=" + id +
                ", primaryDiagnosis='" + primaryDiagnosis + '\'' +
                ", finalDiagnosis='" + finalDiagnosis + '\'' +
                ", medicine='" + medicine + '\'' +
                ", operations='" + operations + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", patientId=" + patientId +
                ", dateCreate=" + dateCreate +
                ", dateDischarge=" + dateDischarge +
                ", hospitallistid=" + hospitallistid +
                '}';
    }
    public boolean isValid() {
        return primaryDiagnosis != null && !primaryDiagnosis.isEmpty() &&
                medicine != null && !medicine.isEmpty() &&
                operations != null && !operations.isEmpty() &&
                dateCreate != null && patientId != null &&
                doctorName != null && !doctorName.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HospitalList that = (HospitalList) o;
        return primaryDiagnosis.equals(that.primaryDiagnosis) &&
                medicine.equals(that.medicine) &&
                operations.equals(that.operations) &&
                doctorName.equals(that.doctorName) &&
                dateCreate.equals(that.dateCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryDiagnosis, medicine, operations, doctorName, dateCreate);
    }
}
