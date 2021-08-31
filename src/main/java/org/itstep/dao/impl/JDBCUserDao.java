package org.itstep.dao.impl;

import javassist.NotFoundException;
import org.itstep.dao.UserDao;
import org.itstep.model.entity.User;
import org.itstep.model.entity.enums.Role;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static org.itstep.dao.impl.UtilityDao.*;


public class JDBCUserDao implements UserDao, AutoCloseable {
    private Connection connection;
    Logger log = Logger.getLogger(JDBCUserDao.class.getName());
    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {
        String SQLUser = "insert into users (username, password, authorities_id) values (?,?,?)";
        String SQLRole = "insert into user_role (authorities) values (?)";
        log.info("Start create.");

        try (PreparedStatement psUser = connection.prepareStatement(SQLUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psRole = connection.prepareStatement(SQLRole, Statement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            psUser.setString(1, entity.getUsername());
            psUser.setString(2, entity.getPassword());

            psRole.setString(1, entity.getRole().name());
            int idRole = psRole.executeUpdate();
            log.info("idRole = " + idRole);

            try (ResultSet generatedKeys = psRole.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    log.info("generatedKeys = " + psRole.getGeneratedKeys().getInt(1));
                    psUser.setInt(3, psRole.getGeneratedKeys().getInt(1));
                    psUser.executeUpdate();
                }
                else {
                    connection.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            connection.commit();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) {
        String SQL = "select * from users as u join user_role as ur on u.id=ur.id where u.id= ?";
        User result = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery(
                    "select * from users"
            );
            while (rs.next()) {
                result = Optional.
                        ofNullable(extractFromResultSetUser(rs))
                        .orElseThrow(() -> new NotFoundException("user " + id + " was not found!"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    public Optional<User> findByUsername(String username) {
        String SQL = "select * from users as u join user_role as ur on u.authorities_id=ur.id  where u.username= ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Optional.ofNullable(Optional.of(extractFromResultSetUser(rs))
                        .orElseThrow(() -> new NotFoundException("user " + username + " was not found!")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> resultList = new ArrayList<>();
        Map<Long, User> patients = new HashMap<>();

        try (Statement ps = connection.createStatement()) {
            ResultSet rs = ps.executeQuery(
                    "select * from users"
            );
            while (rs.next()) {
                User patient = extractFromResultSetUser(rs);
                patient = makeUniqueUser(patients, patient);
                resultList.add(patient);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        this.close();
    }


    private static boolean key = true;
    public void init()  {
        if (key) {
            Optional<User> user = findByUsername("admin");
            if (!user.isPresent()) {
                User admin = User.builder()
                        .setUsername("admin")
                        .setRole(Role.ADMIN)
                        .setPassword("1")
                        .build();
                create(admin);            }
        }
        key=false;
    }
}
