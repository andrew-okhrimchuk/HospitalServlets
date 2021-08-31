package org.itstep.model.entity;

import org.itstep.model.entity.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Patient extends User {
    private LocalDate birthDate;
    private Doctor doctor;
    private List<Nurse> nurses;
    private boolean iscurrentpatient;
    private Builder builder;

    public Patient() {
    }

    public Patient(Long id, String username, Role role, String password, LocalDate birthDate, Doctor doctor, List<Nurse> nurses, boolean iscurrentpatient) {
        super(id, username, role, password);
        this.birthDate = birthDate;
        this.doctor = doctor;
        this.nurses = nurses;
        this.iscurrentpatient = iscurrentpatient;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(List<Nurse> nurses) {
        this.nurses = nurses;
    }

    public boolean isIscurrentpatient() {
        return iscurrentpatient;
    }

    public void setIscurrentpatient(boolean iscurrentpatient) {
        this.iscurrentpatient = iscurrentpatient;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "birthDate=" + birthDate +
                ", doctor=" + doctor +
                ", nurses=" + nurses +
                ", iscurrentpatient=" + iscurrentpatient +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return iscurrentpatient == patient.iscurrentpatient &&
                Objects.equals(birthDate, patient.birthDate) &&
                Objects.equals(doctor, patient.doctor) &&
                Objects.equals(nurses, patient.nurses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthDate, doctor, nurses, iscurrentpatient);
    }
    public static Builder newBuilder() {
        return new Patient().new Builder();
    }

    public class Builder  {

        private Builder() {
            super();
        }

        public Patient.Builder setId(long id) {
            setid(id);
            return this;
        }
        public Patient.Builder setPassword(String password) {
            setpassword(password);
            return this;
        }
        public Patient.Builder setUsername(String username) {
            setusername(username);
            return this;
        }
        public Patient.Builder setRole(Role role) {
            setrole(role);
            return this;
        }
        public Patient.Builder setBirthDate(LocalDate birthDate) {
            Patient. this.birthDate = birthDate;
            return this;
        }
        public Patient.Builder setDoctor(Doctor doctor) {
            Patient.  this.doctor = doctor;
            return this;
        }
        public Patient.Builder setNurses(List<Nurse> nurses) {
            Patient.  this.nurses = nurses;
            return this;
        }
        public Patient.Builder setIscurrentpatient(boolean iscurrentpatient) {
            Patient.  this.iscurrentpatient = iscurrentpatient;
            return this;
        }

        public Patient build() {
            return Patient.this;
        }
    }

}
