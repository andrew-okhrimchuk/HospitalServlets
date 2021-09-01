package org.itstep.dao;


import org.itstep.exeption.DaoExeption;
import org.itstep.model.entity.HospitalList;
import java.util.List;
import java.util.Optional;

public interface HospitalListDao extends GenericDao<HospitalList> {
    Optional<HospitalList> findByParientIdAndDoctorName(int userId, String userNameDoctor) throws DaoExeption;
    }
