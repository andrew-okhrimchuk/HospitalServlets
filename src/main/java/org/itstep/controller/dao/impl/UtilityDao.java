package org.itstep.controller.dao.impl;

import org.itstep.model.entity.User;
import org.itstep.model.entity.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UtilityDao {
   /* static Doctor extractFromResultSetD(ResultSet rs)
            throws SQLException {
        Doctor result = new Doctor();

        result.setId(rs.getInt("iddoctor") );
        result.setName( rs.getString("doctor.name") );
        result.setTelephone( rs.getString("telephone"));
        result.setRoom(rs.getInt("cabinet"));

        return result;
    }*/

    static User extractFromResultSetUser(ResultSet rs)
            throws SQLException {
        User result = new User();

        result.setId(rs.getLong("id") );
        result.setUsername(rs.getString("username"));
        result.setPassword(rs.getString("password"));
        result.setRole(Role.valueOf(rs.getString("authorities").trim().toUpperCase()));
        return result;
    }

   /* static Doctor makeUniqueDoctor(Map<Integer, Doctor> doctors,
                                   Doctor doctor) {
        doctors.putIfAbsent(doctor.getId(), doctor);
        return doctors.get(doctor.getId());
    }*/

    static User makeUniquePatient(
            Map<Long, User> patients, User patient) {
        patients.putIfAbsent(patient.getId(), patient);
        return patients.get(patient.getId());
    }
}
