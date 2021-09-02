package org.itstep.service.interfaces;

import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Doctor;
import java.util.List;

public interface IDoctorService extends GenericService <Doctor, SelectDTO> {

    List<Doctor> getAll(SelectDTO selectDTO) throws ServiceExeption;
    Doctor save(DoctorDTO patientDTO) throws ServiceExeption;
    List<DoctorDTO> getAll() throws ServiceExeption;
    List<DoctorDTO> findAllWithCount() throws ServiceExeption;
}
