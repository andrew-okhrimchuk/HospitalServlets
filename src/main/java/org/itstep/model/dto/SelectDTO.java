package org.itstep.model.dto;


import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.Patient;
import org.itstep.model.entity.enums.Role;
import java.util.ArrayList;
import java.util.List;


public class SelectDTO {
    private Boolean isSortByDateOfBirth = false;
    private Boolean isShowAllCurrentPatients = true;
    private String userNameDoctor;
    private List<Role> authorities = new ArrayList<>();
    private List<Patient> patient = new ArrayList<>();
    private List<DoctorDTO> doctors = new ArrayList<>();
    private List<String> specialities;
    private String speciality;
    private int currentPage;
    private int pageSize;

    public Boolean getSortByDateOfBirth() {
        return isSortByDateOfBirth;
    }

    public void setSortByDateOfBirth(Boolean sortByDateOfBirth) {
        isSortByDateOfBirth = sortByDateOfBirth;
    }

    public Boolean getShowAllCurrentPatients() {
        return isShowAllCurrentPatients;
    }

    public void setShowAllCurrentPatients(Boolean showAllCurrentPatients) {
        isShowAllCurrentPatients = showAllCurrentPatients;
    }

    public String getUserNameDoctor() {
        return userNameDoctor;
    }

    public void setUserNameDoctor(String userNameDoctor) {
        this.userNameDoctor = userNameDoctor;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<Patient> getPatient() {
        return patient;
    }

    public void setPatient(List<Patient> patient) {
        this.patient = patient;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DoctorDTO> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDTO> doctors) {
        this.doctors = doctors;
    }

    public static Builder newBuilder() {
        return new SelectDTO().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setSortByDateOfBirth(Boolean sortByDateOfBirth) {
            SelectDTO.this.isSortByDateOfBirth = sortByDateOfBirth;
            return this;
        }

        public Builder setShowAllCurrentPatients(Boolean showAllCurrentPatients) {
            isShowAllCurrentPatients = showAllCurrentPatients;
            return this;
        }

        public Builder setUserNameDoctor(String userNameDoctor) {
            SelectDTO.this.userNameDoctor = userNameDoctor;
            return this;
        }

        public Builder setAuthorities(List<Role> authorities) {
            SelectDTO.this.authorities = authorities;
            return this;
        }

        public Builder setSpecialities(List<String> specialities) {
            SelectDTO.this.specialities = specialities;
            return this;
        }

        public Builder setSpeciality(String speciality) {
            SelectDTO.this.speciality = speciality;
            return this;
        }

        public Builder setPatientDTO(List<Patient> patientDTO) {
            SelectDTO.this.patient = patientDTO;
            return this;
        }

        public Builder setCurrentPage(int currentPage) {
            SelectDTO.this.currentPage = currentPage;
            return this;

        }

        public Builder setPageSize(int pageSize) {
            SelectDTO.this.pageSize = pageSize;
            return this;

        }

        public SelectDTO build() {
            return SelectDTO.this;
        }
    }

    @Override
    public String toString() {
        return "SelectDTO{" +
                "isSortByDateOfBirth=" + isSortByDateOfBirth +
                ", isShowAllCurrentPatients=" + isShowAllCurrentPatients +
                ", userNameDoctor='" + userNameDoctor + '\'' +
                ", authorities=" + authorities +
                ", patient=" + patient +
                ", doctors=" + doctors +
                ", specialities=" + specialities +
                ", speciality='" + speciality + '\'' +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}

