package org.itstep.dao;

import org.itstep.exeption.DaoExeption;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends  AutoCloseable {
    //TODO return not void
    void create (T entity) throws DaoExeption;
   Optional<T> findById(long id) throws DaoExeption;
    List<T> findAll()throws DaoExeption;
    void update(T entity) throws DaoExeption;
    void delete(int id);
}
