package org.itstep.controller.dao.impl;

import javassist.NotFoundException;
import org.itstep.controller.dao.UserDao;
import org.itstep.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static org.itstep.controller.dao.impl.UtilityDao.*;


public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User findById(int id) {
        String SQL = "select * from users as u join user_role as ur on u.authorities_id=ur.user_id where u.id= ?";
        User result = null;
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, id);
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
        return result;
    }

    public Optional<User> findByUsername(String username) {
        String SQL = "select * from users as u join user_role as ur on u.authorities_id=ur.user_id  where u.username= ?";

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
                patient = makeUniquePatient(patients, patient);
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
    public void close() throws Exception {

    }
}
