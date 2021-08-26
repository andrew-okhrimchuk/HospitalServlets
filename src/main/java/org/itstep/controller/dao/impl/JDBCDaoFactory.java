package org.itstep.controller.dao.impl;

import org.itstep.controller.dao.DaoFactory;
import org.itstep.controller.dao.UserDao;
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
  /*  @Override
    public DoctorDao createDoctorDao() {
        return new JDBCDoctorDao(getConnection());
    }*/
    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "user_1" ,
                    "user_1" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
