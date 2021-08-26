DROP TABLE IF EXISTS USERS CASCADE;
DROP TABLE IF EXISTS PATIENT CASCADE;
DROP TABLE IF EXISTS DOCTOR CASCADE;
DROP TABLE IF EXISTS USER_ROLE CASCADE;
DROP TABLE IF EXISTS Speciality CASCADE;

CREATE TABLE IF NOT EXISTS USER_ROLE
(
    user_id     INTEGER UNIQUE ,
    authorities VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS USERS
(
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(255) NOT NULL unique ,
    password        VARCHAR(255) NOT NULL,
    authorities_id     integer,
    FOREIGN KEY (authorities_id) REFERENCES USER_ROLE (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS DOCTOR
(
    id SERIAL PRIMARY KEY references USERS
);

CREATE TABLE IF NOT EXISTS Speciality
(
    user_id    INTEGER UNIQUE,
    FOREIGN KEY (user_id) REFERENCES DOCTOR (id) ON UPDATE CASCADE ON DELETE CASCADE,
    speciality VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS PATIENT
(
    id               SERIAL PRIMARY KEY references USERS,
    iscurrentpatient boolean default false,
    birthDate        date not null,
    doctor_id        integer,
    FOREIGN KEY (doctor_id) REFERENCES DOCTOR (id)
);
CREATE INDEX birthDate_index ON PATIENT (birthDate);


INSERT INTO USER_ROLE (user_id, authorities )
VALUES (1, 'Patient'),
       (2, 'Patient'),
       (3, 'Patient'),
       (4, 'Patient'),
       (5, 'Patient'),
       (6, 'Patient'),
       (7, 'Patient'),
       (8, 'Patient'),
       (9, 'Patient'),
       (10, 'Patient');

INSERT INTO USERS (id, username, password, authorities_id)
VALUES (1, 'Jon Dou', '$2a$10$ImeH8oWiZpV/cBDXL14ILO8QLWL5Qf1qDmrey8lC1UfMgL9MFv06K', 1),
       (2, 'Amanta Smit', 1, 2),
       (3, 'May Smit', 1, 3),
       (4, 'Andrey Okhrim', 1, 4),
       (5, 'Max Payne', 1, 5),
       (6, 'Max Payne-2', 1, 6),
       (7, 'Max Payne-3', 1, 7),
       (8, 'Max Payne-4', 1, 8),
       (9, 'Max Payne-5', 1, 9),
       (10, 'Max Payne-6', 1, 10);

INSERT INTO DOCTOR (id)
VALUES (7),
       (8);


INSERT INTO Speciality (user_id, speciality)
VALUES (7, 'LORE'),
       (8, 'GYNECOLOGIST');
