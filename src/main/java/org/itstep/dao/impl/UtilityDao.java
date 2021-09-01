package org.itstep.dao.impl;

import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.Patient;
import org.itstep.model.entity.User;
import org.itstep.model.entity.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

public class UtilityDao {
    static Logger log = Logger.getLogger(UtilityDao.class.getName());


    static User extractFromResultSetUser(ResultSet rs)
            throws SQLException {
        User result = new User();

        result.setid(rs.getLong("id"));
        result.setusername(rs.getString("username"));
        result.setpassword(rs.getString("password"));
        result.setrole(Role.valueOf(rs.getString("authorities").trim().toUpperCase()));
        return result;
    }

    static Patient extractFromResultSetPatient(ResultSet rs)
            throws SQLException {
        Patient result = new Patient();
        Doctor doctor = Doctor.newBuilder().setId(rs.getLong("doctor_id")).build();

        result.setid(rs.getLong("id"));
        result.setusername(rs.getString("username"));
        result.setpassword(rs.getString("password"));
        result.setrole(Role.valueOf(rs.getString("authorities").trim().toUpperCase()));
        result.setBirthDate(rs.getDate("birthdate").toLocalDate());
        result.setIscurrentpatient(rs.getBoolean("iscurrentpatient"));
        result.setDoctor(doctor);
        return result;
    }

    static Doctor extractFromResultSetDoctorWithCount(ResultSet rs)
            throws SQLException {
        Doctor result = new Doctor();
        result.setid(rs.getLong("id"));
        result.setusername(rs.getString("username"));
        result.setCountOfPatients(String.valueOf(rs.getInt(4)));
        return result;
    }

    static HospitalList extractFromResultSeHospitalList(ResultSet rs)
            throws SQLException {

        HospitalList result = new HospitalList();
        result.setId(rs.getLong("id"));
        result.setPrimaryDiagnosis(rs.getString("primarydiagnosis"));
        result.setFinalDiagnosis(isThere(rs, "finaldiagnosis") ? rs.getString("finaldiagnosis") : null);
        result.setMedicine(rs.getString("medicine"));
        result.setOperations(rs.getString("operations"));
        result.setDoctorName(rs.getString("doctorname"));
        result.setPatientId(Patient.newBuilder().setId(rs.getLong("patientid")).build());
        result.setDateCreate(rs.getTimestamp("datecreate").toLocalDateTime());
        result.setDateDischarge(isThere(rs, "datedischarge") ? rs.getTimestamp("datedischarge").toLocalDateTime() : null);
        log.info("result = " + result);
        return result;
    }

    static Doctor extractFromResultSetDoctor(ResultSet rs) throws SQLException {
        return Doctor.newBuilder()
                .setId(rs.getLong("id"))
                .setUsername(rs.getString("username"))
                .setSpeciality(isThere(rs, "speciality") ? rs.getString("speciality") : null)
                .build();
    }
    private static boolean isThere(ResultSet rs, String column)
    {
        try
        {
            return rs.findColumn(column) > 0 && rs.getObject(column) !=null;

        } catch (SQLException sqlex)
        {
            log.info("column doesn't exist {}" + column);
        }
        return false;
    }

    static Doctor makeUniqueDoctor(Map<Long, Doctor> doctors,
                                   Doctor doctor) {
        doctors.putIfAbsent(doctor.getId(), doctor);
        return doctors.get(doctor.getId());
    }

    static User makeUniqueUser(
            Map<Long, User> patients, User patient) {
        patients.putIfAbsent(patient.getId(), patient);
        return patients.get(patient.getId());
    }

    static Patient makeUniquePatient(
            Map<Long, Patient> patients, Patient patient) {
        patients.putIfAbsent(patient.getId(), patient);
        return patients.get(patient.getId());
    }
    static HospitalList makeUniqueHospitalList(
            Map<Long, HospitalList> patients, HospitalList patient) {
        patients.putIfAbsent(patient.getId(), patient);
        return patients.get(patient.getId());
    }
}
