package org.itstep.dao.impl;

import org.itstep.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL DataSource unable to load PostgreSQL JDBC Driver");
        }
    }

    @Override
    public PatientDao createPatientDao() {
        JDBCPatientDao dao = new JDBCPatientDao(getConnection());
        dao.init();
        return dao;
    }

    @Override
    public DoctorDao createDoctorDao() {
        JDBCDoctorDao dao = new JDBCDoctorDao(getConnection());
        dao.init();
        return dao;
    }

    @Override
    public UserDao createUserDao() {
        JDBCUserDao dao = new JDBCUserDao(getConnection());
        dao.init();
        return dao;
    }
    @Override
    public HospitalListDao createHospitalListDao() {
        return new JDBCHospitalListDao(getConnection());
    }
    public MedicationLogDao createMedicationLogDao() {return new JDBCMedicationLogDao(getConnection());}
    public NurseDao createNurseDao() {return new JDBCNurseDao(getConnection()); }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres2",
                    "user_1",
                    "user_1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
