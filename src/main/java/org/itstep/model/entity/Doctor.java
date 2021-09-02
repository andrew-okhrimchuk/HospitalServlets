package org.itstep.model.entity;

import org.itstep.model.entity.enums.Role;

import java.util.Objects;

public class Doctor extends User {

    private String speciality;
    private String countOfPatients;

    public Doctor() {
        super();
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCountOfPatients() {
        return countOfPatients;
    }

    public void setCountOfPatients(String countOfPatients) {
        this.countOfPatients = countOfPatients;
    }
    public static Builder newBuilder() {
        return new Doctor().new Builder();
    }

    public class Builder {

        private Builder() {
            super();
        }
        public Doctor.Builder setId(long id) {
            setid(id);
            return this;
        }
        public Doctor.Builder setPassword(String password) {
            setpassword(password);
            return this;
        }
        public Doctor.Builder setUsername(String username) {
            setusername(username);
            return this;
        }
        public Doctor.Builder setRole(Role role) {
            setrole(role);
            return this;
        }

        public Doctor.Builder setSpeciality(String speciality) {
            Doctor.this.speciality = speciality;
            return this;
        }

        public Doctor.Builder setCountOfPatients(String countOfPatients) {
            Doctor.  this.countOfPatients = countOfPatients;
            return this;
        }

        public Doctor build() {
            return Doctor.this;
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + getId() + '\'' +
                "speciality='" + speciality + '\'' +
                "username='" + this.getUsername() + '\'' +
                "role='" + this.getRole() + '\'' +
                "password='" + this.getPassword() + '\'' +
                ", countOfPatients='" + countOfPatients + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return speciality.equals(doctor.speciality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speciality);
    }
}
