package org.itstep.dao.impl;

import javassist.NotFoundException;
import org.itstep.dao.HospitalListDao;
import org.itstep.dao.PatientDao;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.Patient;
import org.itstep.model.entity.enums.Role;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

import static org.itstep.dao.impl.UtilityDao.*;


public class JDBCHospitalListDao implements HospitalListDao, AutoCloseable {
    private Connection connection;
    Logger log = Logger.getLogger(JDBCHospitalListDao.class.getName());

    public JDBCHospitalListDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(HospitalList entity) throws DaoExeption {
        String SQLHospitalList = "insert into hospitallist (primarydiagnosis, medicine, operations, doctorname, patientid, datecreate) values (?,?,?,?,?,?)";

        try (PreparedStatement psHospitalList = connection.prepareStatement(SQLHospitalList, Statement.RETURN_GENERATED_KEYS);
        ) {
            psHospitalList.setString(1, entity.getPrimaryDiagnosis());
            psHospitalList.setString(2, entity.getMedicine());
            psHospitalList.setString(3, entity.getOperations());
            psHospitalList.setString(4, entity.getDoctorName());
            psHospitalList.setLong(5, entity.getPatientId().getId());
            psHospitalList.setTimestamp(6, Timestamp.valueOf(entity.getDateCreate()));

            psHospitalList.executeUpdate();
        } catch (Exception e) {
            try {
                connection.rollback();
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
    public Optional<HospitalList> findById(long id) throws DaoExeption {
        return null;
    }

    @Override
    public List<HospitalList> findAll() {
        return null;
    }

    @Override
    public void update(HospitalList entity) throws DaoExeption {
        String SQLhl = "UPDATE hospitallist as h SET primarydiagnosis=?, medicine=?, operations=? WHERE h.id=?";

        try (PreparedStatement statement = connection.prepareStatement(SQLhl, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, entity.getPrimaryDiagnosis());
            statement.setString(2, entity.getMedicine());
            statement.setString(3, entity.getOperations());
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            try {
                connection.rollback();
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
    public Optional<HospitalList> findByParientIdAndDoctorName(int userId, String doctorName) throws DaoExeption {
        log.info("Start findAll by userId, doctorName " + userId + doctorName);

        Optional<HospitalList> resultList =Optional.empty();
        Map<Long, HospitalList> patients = new HashMap<>();
        String SQL = "select * from hospitallist where h.patientid = ? and h.doctorname=? and h.datedischarge is null";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, userId);
            ps.setString(2, doctorName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList = Optional.ofNullable(Optional.of(extractFromResultSeHospitalList(rs))
                        .orElseThrow(() -> new NotFoundException("userId " + userId + " was not found!")));
            }
            log.info("find HospitalList count = " + resultList.isPresent());

        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }
}
