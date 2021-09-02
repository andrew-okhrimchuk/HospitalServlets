package org.itstep.dao.impl;

import javassist.NotFoundException;
import org.itstep.dao.DoctorDao;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.enums.Role;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static org.itstep.dao.impl.UtilityDao.*;


public class JDBCDoctorDao implements DoctorDao {
    private Connection connection;
    Logger log = Logger.getLogger(JDBCDoctorDao.class.getName());

    public JDBCDoctorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Doctor entity) {
        log.info("create Doctor, entity = " + entity);

        String SQLUser = "insert into users (username, password, authorities_id) values (?,?,?)";
        String SQLRole = "insert into user_role (authorities) values (?)";
        String SQLSpeciality = "insert into speciality (user_id, speciality) values (?,?)";
        String SQLDoc = "insert into doctor (id) values (?)";
        log.info("Start create.");

        try (PreparedStatement psUser = connection.prepareStatement(SQLUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psRole = connection.prepareStatement(SQLRole, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psDoc = connection.prepareStatement(SQLDoc, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psSpeciality = connection.prepareStatement(SQLSpeciality, Statement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            psUser.setString(1, entity.getUsername());
            psUser.setString(2, entity.getPassword());

            psRole.setString(1, entity.getRole().name());
            psRole.executeUpdate();

            try (ResultSet generatedKeys = psRole.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    log.info("generatedKeys = " + psRole.getGeneratedKeys().getInt(1));
                    psUser.setInt(3, psRole.getGeneratedKeys().getInt(1));
                    psUser.executeUpdate();

                    try (ResultSet generatedKeysUser = psUser.getGeneratedKeys()) {
                        if (generatedKeysUser.next()) {
                            log.info("generatedKeys psUser = " + psUser.getGeneratedKeys().getInt(1));
                            psDoc.setInt(1, psUser.getGeneratedKeys().getInt(1));
                            psDoc.executeUpdate();

                            psSpeciality.setInt(1, psUser.getGeneratedKeys().getInt(1));
                            psSpeciality.setString(2, entity.getSpeciality());
                            psSpeciality.executeUpdate();
                        }
                        else {
                            connection.rollback();
                            throw new SQLException("Creating user failed, no ID obtained.");
                        }
                    }
                }
                else {
                    connection.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            connection.commit();
        } catch (Exception e) {
            log.info("Exception create. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Doctor> findById(long id)  throws DaoExeption{
        String SQL = "select * from users as u join user_role as ur on u.id=ur.id join doctor d on d.id=u.id left join speciality as sp on sp.user_id=u.id where d.id= ?";
        Doctor result = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = Optional.
                        ofNullable(extractFromResultSetDoctor(rs))
                        .orElseThrow(() -> new NotFoundException("user " + id + " was not found!"));
            }
        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return Optional.ofNullable(result);
    }

    public Optional<Doctor> findByUsername(String username) {
        String SQL = "select * from users as u join user_role as ur on u.authorities_id=ur.id left join speciality as sp on sp.user_id=u.id  where u.username= ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Optional.ofNullable(Optional.of(extractFromResultSetDoctor(rs))
                        .orElseThrow(() -> new NotFoundException("user " + username + " was not found!")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Doctor> findAll() throws DaoExeption {
        log.info("Start findAll.");

        List<Doctor> resultList = new ArrayList<>();
        Map<Long, Doctor> patients = new HashMap<>();
        String SQL = "select * from users as u join doctor as d on d.id = u.id join user_role as ur on u.id = ur.id left join speciality as sp on sp.user_id=u.id where ur.authorities = 'DOCTOR' order by u.username";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = extractFromResultSetDoctor(rs);
                doctor = makeUniqueDoctor(patients, doctor);
                resultList.add(doctor);
            }
            log.info("findAll Doctor count = " + resultList.size());
            log.info("findAll Doctors = " + resultList);

        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public void update(Doctor entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public List<Doctor> findAll(SelectDTO select) throws DaoExeption {
        log.info("Start findAll by SelectDTO " + select);
        List<Doctor> resultList = new ArrayList<>();
        Map<Long, Doctor> patients = new HashMap<>();
        String SQL;
       if (select.getSpeciality()==null || select.getSpeciality().equals("ALL")){
            SQL = "select * from users as u join doctor as d on d.id = u.id join user_role as ur on u.id = ur.id left join speciality as sp on sp.user_id=u.id where ur.authorities = 'DOCTOR' order by u.username";
       } else {
            SQL = "select * from users as u join doctor as d on d.id = u.id join user_role as ur on u.id = ur.id left join speciality as sp on sp.user_id=u.id where ur.authorities = 'DOCTOR' and sp.speciality = ? order by u.username";
       }
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            if (select.getSpeciality()!=null && !select.getSpeciality().equals("ALL")) {
                ps.setString(1, select.getSpeciality());
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = extractFromResultSetDoctor(rs);
                doctor = makeUniqueDoctor(patients, doctor);
                resultList.add(doctor);
            }
            log.info("findAll Doctor count = " + resultList.size());
            log.info("findAll Doctors = " + resultList);

        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public List<Doctor> findAllWithCount() throws DaoExeption {
        log.info("Start findAllWithCount " );

        List<Doctor> resultList = new ArrayList<>();
        Map<Long, Doctor> patients = new HashMap<>();
        String SQL = "select u.username, p.doctor_id,  d.id, count( p.doctor_id) as zz\n" +
                "                from doctor d join users u on u.id = d.id\n" +
                "                            left join patient p on d.id = p.doctor_id\n" +
                "                group by p.doctor_id, u.username, d.id";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = extractFromResultSetDoctorWithCount(rs);
                doctor = makeUniqueDoctor(patients, doctor);
                resultList.add(doctor);
            }
        } catch (Exception e) {
            log.info("Error findAllWithCount " +  e.getMessage());
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    private static boolean key = true;
    public void init()  {
        if (key) {
            key=false;
            Optional<Doctor> doc = findByUsername("doc");
            log.info("Start isPresent  " + doc.isPresent());
            if (!doc.isPresent()) {
                Doctor admin = Doctor.newBuilder()
                        .setUsername("doc")
                        .setRole(Role.DOCTOR)
                        .setPassword("1")
                        .build();
                create(admin);            }
        }
    }
}
