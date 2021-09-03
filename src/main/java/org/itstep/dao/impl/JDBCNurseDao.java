package org.itstep.dao.impl;

import org.itstep.dao.NurseDao;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.Nurse;
import org.itstep.model.entity.Patient;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static org.itstep.dao.impl.UtilityDao.*;


public class JDBCNurseDao implements NurseDao, AutoCloseable {
    private Connection connection;
    Logger log = Logger.getLogger(JDBCNurseDao.class.getName());

    public JDBCNurseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Nurse entity) throws DaoExeption {
    }

    @Override
    public Optional<Nurse> findById(long id) throws DaoExeption {
        return null;
    }

    @Override
    public List<Nurse> findAll() {
        return null;
    }

    @Override
    public void update(Nurse entity) throws DaoExeption {
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public void close() {
        this.close();
    }

    @Override
    public List<Nurse> findByUsername(String username) throws DaoExeption {
        log.info("Start findByUsername by userId " + username);

        List<Nurse> resultList = new ArrayList<>();
        Map<Long, Nurse> map = new HashMap<>();
        String SQL = "select * from users as u join nurse as n on u.id=n.id join user_role as ur on ur.id=u.id where u.username = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nurse medicationLog = extractFromResultSetNurse(rs);
                medicationLog = makeUniqueNurse(map, medicationLog);
                resultList.add(medicationLog);
            }
            log.info("find Nurse count = " + resultList);
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("connection.close " + throwables.getMessage());
                throwables.printStackTrace();
            }
            log.info("Exception findByUsername " + e.getMessage());
            e.printStackTrace();
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public List<Nurse> findNursesByPatientsIsNotContaining(long patientId) throws DaoExeption {
        log.info("Start findNursesByPatientsIsNotContaining by patient " + patientId);

        List<Nurse> resultList = new ArrayList<>();
        Map<Long, Nurse> map = new HashMap<>();
        String SQL = "select * from users as u join nurse as n on u.id=n.id join user_role as ur on ur.id=u.id left join patientnurse as pn on pn.nurses_id=n.id where patients_user_id != ? or patients_user_id is null";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, patientId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nurse medicationLog = extractFromResultSetNurse(rs);
                medicationLog = makeUniqueNurse(map, medicationLog);
                resultList.add(medicationLog);
            }
            log.info("find Nurse count = " + resultList);
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("connection.close " + throwables.getMessage());
                throwables.printStackTrace();
            }
            log.info("Exception findByPatients " + e.getMessage());
            e.printStackTrace();
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public List<Nurse> findByPatientId(long patientId) throws DaoExeption {
        log.info("Start findByUsername by patientId " + patientId);

        List<Nurse> resultList = new ArrayList<>();
        Map<Long, Nurse> map = new HashMap<>();
        String SQL = "select * from users as u join nurse as n on u.id=n.id join user_role as ur on ur.id=u.id join patientnurse as pn on pn.nurses_id=n.id where pn.patients_user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, patientId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nurse medicationLog = extractFromResultSetNurse(rs);
                medicationLog = makeUniqueNurse(map, medicationLog);
                resultList.add(medicationLog);
            }
            log.info("find Nurse count = " + resultList);
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("connection.close " + throwables.getMessage());
                throwables.printStackTrace();
            }
            log.info("Exception findByPatients " + e.getMessage());
            e.printStackTrace();
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public boolean addPatientToNurse(long userId, long nurseId) throws DaoExeption {
        String SQLHospitalList = "insert into patientnurse (patients_user_id, nurses_id) values (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(SQLHospitalList, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setLong(1, userId);
            statement.setLong(2, nurseId);
            int result = statement.executeUpdate();
            connection.close();
            return result > 0;
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("throwables = " + throwables.getMessage());
                throwables.printStackTrace();
            }
            log.info("Exception = " + e.getMessage());
            throw new DaoExeption(e.getMessage(), e);
        }
    }
    @Override
    public boolean deletePatientToNurse(long userId, long nurseId) throws DaoExeption {
        String SQLHospitalList = "DELETE from patientnurse where patients_user_id=? and nurses_id= ?";

        try (PreparedStatement statement = connection.prepareStatement(SQLHospitalList, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setLong(1, userId);
            statement.setLong(2, nurseId);
            int result = statement.executeUpdate();
            connection.close();
            return result > 0;
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("throwables = " + throwables.getMessage());
                throwables.printStackTrace();
            }
            log.info("Exception = " + e.getMessage());
            throw new DaoExeption(e.getMessage(), e);
        }
    }
}
