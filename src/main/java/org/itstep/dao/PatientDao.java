package org.itstep.dao;


import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.Patient;

import java.util.List;

public interface PatientDao extends GenericDao<Patient> {
    List<Patient> findAll(SelectDTO select) throws DaoExeption;
    List<Patient> findAllByDoctorName(SelectDTO select) throws DaoExeption;
    List<Patient> findAllByNurseName(String name) throws DaoExeption;
    }
