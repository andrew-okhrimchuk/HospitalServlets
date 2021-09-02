package org.itstep.model.entity;

import java.time.LocalDateTime;

public class MedicationLog {
    private Long id;
    private Long hospitallistid;
    private String description;
    private String executor;
    private String doctorName;
    private LocalDateTime dateCreate = LocalDateTime.now();
    private LocalDateTime dateEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHospitallistid() {
        return hospitallistid;
    }

    public void setHospitallistid(Long hospitallistid) {
        this.hospitallistid = hospitallistid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "MedicationLog{" +
                "id=" + id +
                ", hospitallistid=" + hospitallistid +
                ", description='" + description + '\'' +
                ", executor='" + executor + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", dateCreate=" + dateCreate +
                ", dateEnd=" + dateEnd +
                '}';
    }

    public boolean isValidAdd() {
        return hospitallistid != null  &&
                description != null && !description.isEmpty() &&
                doctorName != null && !doctorName.isEmpty() &&
                dateCreate != null;
    }
    public boolean isValidDone() {
        return  executor != null && !executor.isEmpty() &&
                dateEnd != null;
    }
}
