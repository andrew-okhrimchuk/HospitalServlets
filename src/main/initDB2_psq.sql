DROP TABLE IF EXISTS USERS CASCADE;
DROP TABLE IF EXISTS PATIENT CASCADE;
DROP TABLE IF EXISTS DOCTOR CASCADE;
DROP TABLE IF EXISTS USER_ROLE CASCADE;
DROP TABLE IF EXISTS Speciality CASCADE;
DROP TABLE IF EXISTS HospitalList CASCADE;
DROP TABLE IF EXISTS MedicationLog CASCADE;
DROP TABLE IF EXISTS NURSE CASCADE;
DROP TABLE IF EXISTS PatientNurse CASCADE;


CREATE TABLE IF NOT EXISTS USER_ROLE
(
    id          SERIAL UNIQUE,
    authorities VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS USERS
(
    id             SERIAL PRIMARY KEY,
    username       VARCHAR(255) NOT NULL unique,
    password       VARCHAR(255) NOT NULL,
    authorities_id integer,
    FOREIGN KEY (authorities_id) REFERENCES USER_ROLE (id) ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE IF NOT EXISTS DOCTOR
(
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES USERS (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Speciality
(
    user_id    INTEGER UNIQUE,
    FOREIGN KEY (user_id) REFERENCES DOCTOR (id) ON UPDATE CASCADE ON DELETE CASCADE,
    speciality VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS PATIENT
(
    id               integer PRIMARY KEY references USERS,
    iscurrentpatient boolean default false,
    birthDate        date not null,
    doctor_id        integer,
    FOREIGN KEY (doctor_id) REFERENCES DOCTOR (id)
);
CREATE INDEX birthDate_index ON PATIENT (birthDate);

CREATE TABLE IF NOT EXISTS HospitalList
(
    id               SERIAL PRIMARY KEY,
    primaryDiagnosis VARCHAR(255) NOT NULL,
    finalDiagnosis   VARCHAR(255),
    medicine         VARCHAR(255),
    operations       VARCHAR(255),
    doctorName       VARCHAR(255),
    patientId        integer,
    dateCreate       date         not null,
    dateDischarge    date
);


CREATE TABLE IF NOT EXISTS MedicationLog
(
    id               SERIAL PRIMARY KEY,
    hospitallistid integer NOT NULL,
    description   VARCHAR(255),
    executor         VARCHAR(255),
    doctorName       VARCHAR(255),
    dateCreate       timestamp         not null,
    dateEnd    timestamp,
    FOREIGN KEY (hospitallistid) REFERENCES HospitalList (id) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS NURSE
(
    id               SERIAL PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES USERS (id)
);

CREATE TABLE IF NOT EXISTS PatientNurse
(
    id               SERIAL PRIMARY KEY,
    patients_user_id integer NOT NULL,
    nurses_id integer NOT NULL,
    FOREIGN KEY (patients_user_id) REFERENCES USERS (id),
    FOREIGN KEY (nurses_id) REFERENCES NURSE (id)
);
ALTER TABLE PatientNurse
    ADD CONSTRAINT duble_key UNIQUE(patients_user_id, nurses_id);