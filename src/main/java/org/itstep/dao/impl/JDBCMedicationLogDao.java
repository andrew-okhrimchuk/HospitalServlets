package org.itstep.dao.impl;

import org.itstep.dao.HospitalListDao;
import org.itstep.dao.MedicationLogDao;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.MedicationLog;
import org.itstep.model.entity.Patient;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static org.itstep.dao.impl.UtilityDao.*;
import static org.itstep.dao.impl.UtilityDao.makeUniquePatient;


public class JDBCMedicationLogDao implements MedicationLogDao, AutoCloseable {
    private Connection connection;
    Logger log = Logger.getLogger(JDBCMedicationLogDao.class.getName());

    public JDBCMedicationLogDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(MedicationLog entity) throws DaoExeption {
        String SQLHospitalList = "insert into medicationlog (hospitallistid, description, doctorname, datecreate) values (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(SQLHospitalList, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setLong(1, entity.getHospitallistid());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getDoctorName());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getDateCreate()));

            statement.executeUpdate();
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
    public Optional<MedicationLog> findById(long id) throws DaoExeption {
        return null;
    }

    @Override
    public List<MedicationLog> findAll() {
        return null;
    }

    @Override
    public void update(MedicationLog entity) throws DaoExeption {
        log.info("Start update  " + entity);

        String SQLhl = "UPDATE medicationlog as m SET executor=?, dateend=? WHERE m.id=?";

        try (PreparedStatement statement = connection.prepareStatement(SQLhl, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, entity.getExecutor());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getDateEnd()));
            statement.setLong(3, entity.getId());
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("SQLException throwables = " + throwables.getMessage());
                throwables.printStackTrace();
                throw new DaoExeption(e.getMessage(), e);
            }
            log.info("Exception = " + e.getMessage());
            throw new DaoExeption(e.getMessage(), e);
        }

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        this.close();
    }


    @Override
    public List<MedicationLog> findByPatientId(long userId) throws DaoExeption {
        log.info("Start findByPatientId by userId " + userId);

        List<MedicationLog> resultList = new ArrayList<>();
        Map<Long, MedicationLog> map = new HashMap<>();
        String SQL = "select * from medicationlog m where m.hospitallistid = (select h.id from hospitallist h where h.datedischarge is null and h.patientId=?) order by m.datecreate desc ";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicationLog medicationLog = extractFromResultSeMedicationLog(rs);
                medicationLog = makeUniqueMedicationLog(map, medicationLog);
                resultList.add(medicationLog);
            }
            log.info("find MedicationLog count = " + resultList);
            connection.close();
        } catch (Exception e) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                log.info("connection.close " + throwables.getMessage());
                throwables.printStackTrace();
            }
            log.info("Exception findByParientIdAndDoctorName " + e.getMessage());
            e.printStackTrace();
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }
}
