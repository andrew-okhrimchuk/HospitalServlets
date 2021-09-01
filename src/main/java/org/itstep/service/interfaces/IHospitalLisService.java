package org.itstep.service.interfaces;

import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.HospitalList;
import java.util.Optional;

public interface IHospitalLisService extends GenericService <HospitalList, SelectDTO> {
     Optional<HospitalList> findByParientIdAndDoctorName(int userId, String userNameDoctor) throws ServiceExeption;
     void save(HospitalList hospitalList) throws ServiceExeption;
}
