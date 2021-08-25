package org.itstep.controller.dao;


import org.itstep.model.entity.User;

public interface UserDao extends GenericDao<User> {
     User findByUsername(String username);
    }
