package org.itstep.dao;

import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.Nurse;
import org.itstep.model.entity.Patient;

import java.util.List;

public interface NurseDao extends GenericDao<Nurse> {
    List<Nurse> findByUsername(String username) throws DaoExeption;

    List<Nurse> findNursesByPatientsIsNotContaining(long patientId) throws DaoExeption;

    List<Nurse> findByPatientId(long patientId) throws DaoExeption;
}
