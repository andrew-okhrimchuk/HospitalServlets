package org.itstep.dao;


import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.Doctor;
import java.util.List;

public interface DoctorDao extends GenericDao<Doctor> {
    List<Doctor> findAll(SelectDTO select) throws DaoExeption;
    List<Doctor> findAll() throws DaoExeption;
    List<Doctor> findAllWithCount() throws DaoExeption;
    }
