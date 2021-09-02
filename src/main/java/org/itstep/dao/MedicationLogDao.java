package org.itstep.dao;


import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.MedicationLog;

import java.util.List;
import java.util.Optional;

public interface MedicationLogDao extends GenericDao<MedicationLog> {
    List<MedicationLog> findByPatientId(long userId) throws DaoExeption;
    }
