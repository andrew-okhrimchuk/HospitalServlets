package org.itstep.dao.impl;

import javassist.NotFoundException;
import org.itstep.dao.PatientDao;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.Patient;
import org.itstep.model.entity.enums.Role;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import static org.itstep.dao.impl.UtilityDao.*;


public class JDBCPatientDao implements PatientDao, AutoCloseable {
    private Connection connection;
    Logger log = Logger.getLogger(JDBCPatientDao.class.getName());

    public JDBCPatientDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Patient entity) throws DaoExeption {
        String SQLUser = "insert into users (username, password, authorities_id) values (?,?,?)";
        String SQLRole = "insert into user_role (authorities) values (?)";
        String SQLPatient = "insert into patient (id, iscurrentpatient, birthdate, doctor_id) values (?,?,?,?)";

        try (PreparedStatement psUser = connection.prepareStatement(SQLUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psRole = connection.prepareStatement(SQLRole, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psPatient = connection.prepareStatement(SQLPatient, Statement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            psUser.setString(1, entity.getUsername());
            psUser.setString(2, entity.getPassword());

            psRole.setString(1, entity.getRole().name());
            psRole.executeUpdate();

            try (ResultSet generatedKeysRole = psRole.getGeneratedKeys()) {
                if (generatedKeysRole.next()) {
                    log.info("generatedKeysRole = " + psRole.getGeneratedKeys().getInt(1));
                    psUser.setInt(3, psRole.getGeneratedKeys().getInt(1));
                    psUser.executeUpdate();

                    try (ResultSet generatedKeysUser = psUser.getGeneratedKeys()) {
                        if (generatedKeysUser.next()) {
                            log.info("generatedKeys psUser = " + psUser.getGeneratedKeys().getInt(1));
                            psPatient.setInt(1, psUser.getGeneratedKeys().getInt(1));
                            psPatient.setBoolean(2, entity.isIscurrentpatient());
                            psPatient.setDate(3, Date.valueOf(entity.getBirthDate()));
                            if (entity.getDoctor() != null) {
                                psPatient.setLong(4, entity.getDoctor().getId());
                            } else {
                                psPatient.setNull(4, Types.INTEGER);
                            }
                            psPatient.executeUpdate();
                        } else {
                            connection.rollback();
                            throw new SQLException("Creating user failed, no ID obtained.");
                        }
                    }
                } else {
                    connection.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            connection.commit();
            connection.close();
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
    public Optional<Patient> findById(long id) throws DaoExeption {
        String SQL = "select * from users as u join user_role as ur on u.id=ur.id join patient as p on p.id=u.id where u.id= ?";
        Patient result = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = Optional.
                        ofNullable(extractFromResultSetPatient(rs))
                        .orElseThrow(() -> new NotFoundException("user " + id + " was not found!"));
            }
            rs.close();
        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return Optional.ofNullable(result);
    }

    public Optional<Patient> findByUsername(String username) {
        String SQL = "select * from users as u join user_role as ur on u.id=ur.id join patient p on p.id=u.id where u.username= ?";
        log.info("findByUsername = " + username);

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Optional.ofNullable(Optional.of(extractFromResultSetPatient(rs))
                        .orElseThrow(() -> new NotFoundException("user " + username + " was not found!")));
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        log.info("Start findAll doGet ");
        List<Patient> resultList = new ArrayList<>();
        Map<Long, Patient> patients = new HashMap<>();

        try (Statement ps = connection.createStatement()) {
            ResultSet rs = ps.executeQuery(
                    "select * from patient"
            );
            while (rs.next()) {
                Patient patient = extractFromResultSetPatient(rs);
                patient = makeUniquePatient(patients, patient);
                resultList.add(patient);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public void update(Patient entity) throws DaoExeption {
        String SQLUser = "UPDATE users as u SET username=?, password=? WHERE u.id=?";
        String SQLPatient = "UPDATE patient as p SET  iscurrentpatient=?, birthdate=?, doctor_id=?  WHERE p.id=?";

        try (PreparedStatement psUser = connection.prepareStatement(SQLUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psPatient = connection.prepareStatement(SQLPatient, Statement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            psUser.setString(1, entity.getUsername());
            psUser.setString(2, entity.getPassword());
            psUser.setLong(3, entity.getId());
            psUser.executeUpdate();

            psPatient.setBoolean(1, entity.isIscurrentpatient());
            psPatient.setDate(2, Date.valueOf(entity.getBirthDate()));
            psPatient.setLong(3, entity.getDoctor().getId());
            psPatient.setLong(4, entity.getId());
            psPatient.executeUpdate();

            connection.commit();
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
    public List<Patient> findAll(SelectDTO select) throws DaoExeption {
        log.info("Start findAll by SelectDTO " + select);

        List<Patient> resultList = new ArrayList<>();
        Map<Long, Patient> patients = new HashMap<>();
        String SQL = "select * from users as u join patient as p on p.id = u.id join user_role as ur on u.id=ur.id left join doctor as d on d.id = p.doctor_id where p.iscurrentpatient = ? order by ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setBoolean(1, select.getShowAllCurrentPatients());
            ps.setString(2, select.getSortByDateOfBirth() ? "birthDate" : "username");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = extractFromResultSetPatient(rs);
                patient = makeUniquePatient(patients, patient);
                resultList.add(patient);
            }
            log.info("find Patients count = " + resultList.size());

        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public List<Patient> findAllByDoctorName(SelectDTO select) throws DaoExeption {
        log.info("Start findAll by SelectDTO " + select);

        List<Patient> resultList = new ArrayList<>();
        Map<Long, Patient> patients = new HashMap<>();
        String SQL = "select * from users as u join patient as p on p.id = u.id join user_role as ur on u.id=ur.id left join doctor as d on d.id = p.doctor_id where d.id= (select d.id from users as u join doctor as d on u.id=d.id where u.username= ?)  order by ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, select.getUserNameDoctor());
            ps.setString(2, select.getSortByDateOfBirth() ? "birthDate" : "username");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = extractFromResultSetPatient(rs);
                patient = makeUniquePatient(patients, patient);
                resultList.add(patient);
            }
            log.info("find Patients count = " + resultList.size());

        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    @Override
    public List<Patient> findAllByNurseName(String name) throws DaoExeption {
        log.info("Start findAllByNurseName " + name);

        List<Patient> resultList = new ArrayList<>();
        Map<Long, Patient> patients = new HashMap<>();
        String SQL = "select * from users as u join patient as p on p.id = u.id join user_role as ur on u.id=ur.id  join patientnurse as pn on pn.patients_user_id = u.id where pn.nurses_id = (select u.id from users as u where u.username= ?) ";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = extractFromResultSetPatient(rs);
                patient = makeUniquePatient(patients, patient);
                resultList.add(patient);
            }
            log.info("find Patients count = " + resultList.size());

        } catch (Exception e) {
            throw new DaoExeption(e.getMessage(), e);
        }
        return resultList;
    }

    private static boolean key = true;

    public void init() {
        if (key) {
            Optional<Patient> patient1 = findByUsername("patient");
            log.info("Start isPresent  " + patient1.isPresent());
            if (!patient1.isPresent()) {
                Patient admin = Patient.newBuilder()
                        .setUsername("patient")
                        .setRole(Role.PATIENT)
                        .setIscurrentpatient(true)
                        .setBirthDate(LocalDate.now())
                        .setDoctor(null)
                        .setPassword("1")
                        .build();
                try {
                    create(admin);
                } catch (DaoExeption daoExeption) {
                    daoExeption.printStackTrace();
                }
            }
        }
        key = false;
    }
}
