package org.itstep.controller.dao;


import org.itstep.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByUsername(String username);
    }
