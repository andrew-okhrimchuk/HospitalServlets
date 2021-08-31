package org.itstep.model.dto;


public class DoctorDTO {

    private Long id;
    private String countOfPatients;
    private String username;
    private String password;
    private String speciality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountOfPatients() {
        return countOfPatients;
    }

    public void setCountOfPatients(String countOfPatients) {
        this.countOfPatients = countOfPatients;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public static DoctorDTO.Builder newBuilder() {
        return new DoctorDTO().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public DoctorDTO.Builder setId(Long id) {
            DoctorDTO.this.id = id;
            return this;
        }

        public DoctorDTO.Builder setCountOfPatients(String countOfPatients) {
            DoctorDTO.this.countOfPatients = countOfPatients;
            return this;
        }

        public DoctorDTO.Builder setUsername(String username) {
            DoctorDTO.this.username = username;
            return this;
        }

        public DoctorDTO.Builder setPassword(String password) {
            DoctorDTO.this.password = password;
            return this;
        }

        public DoctorDTO.Builder setSpeciality(String speciality) {
            DoctorDTO.this.speciality = speciality;
            return this;
        }

        public DoctorDTO build() {
            return DoctorDTO.this;
        }
    }


    public boolean isValid() {
        return username != null && !username.isEmpty() &&
                password != null && !password.isEmpty() &&
                speciality != null && !speciality.isEmpty();
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", countOfPatients='" + countOfPatients + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", speciality='" + speciality + '\'' +
                '}';
    }
}
