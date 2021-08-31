package org.itstep.model.dto;


import org.itstep.model.entity.enums.Role;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PatientDTO {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String id;
    private String username;
    private String birthDate;
    private String password;
    private Boolean isActualPatient;
    private List<Role> authorities = new ArrayList<>();
    private DoctorDTO doctorDTO;

    public String convertToDatabaseColumn(LocalDate entityDate) {
        return entityDate.format(formatter);
    }

    public LocalDate convertToEntityAttribute(String databaseDate) {
        LocalDate start = LocalDate.parse(databaseDate);
        start.format(formatter);
        return start;
    }

    public boolean isValid(){
        return username!=null && !username.isEmpty() &&
        birthDate!=null && !birthDate.isEmpty()&&
        password!=null && !password.isEmpty();
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static void setFormatter(DateTimeFormatter formatter) {
        PatientDTO.formatter = formatter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActualPatient() {
        return isActualPatient;
    }

    public void setActualPatient(Boolean actualPatient) {
        isActualPatient = actualPatient;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public DoctorDTO getDoctorDTO() {
        return doctorDTO;
    }

    public void setDoctorDTO(DoctorDTO doctorDTO) {
        this.doctorDTO = doctorDTO;
    }
    public static PatientDTO.Builder newBuilder() {
        return new PatientDTO().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public PatientDTO.Builder setId(String id) {
            PatientDTO.this.id = id;
            return this;
        }


        public PatientDTO.Builder setUsername(String username) {
            PatientDTO. this.username = username;
            return this;
        }

        public PatientDTO.Builder setBirthDate(String birthDate) {
            PatientDTO.  this.birthDate = birthDate;
            return this;
        }


        public PatientDTO.Builder setPassword(String password) {
            PatientDTO. this.password = password;
            return this;
        }

        public PatientDTO.Builder setActualPatient(Boolean actualPatient) {
            PatientDTO.this.isActualPatient = actualPatient;
            return this;
        }

        public PatientDTO.Builder setAuthorities(List<Role> authorities) {
            PatientDTO. this.authorities = authorities;
            return this;
        }

        public PatientDTO.Builder setDoctorDTO(DoctorDTO doctorDTO) {
            PatientDTO.  this.doctorDTO = doctorDTO;
            return this;
        }

        public PatientDTO build() {
            return PatientDTO.this;
        }
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", password='" + password + '\'' +
                ", isActualPatient=" + isActualPatient +
                ", authorities=" + authorities +
                ", doctorDTO=" + doctorDTO +
                '}';
    }
}
