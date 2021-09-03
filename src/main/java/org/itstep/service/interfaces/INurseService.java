package org.itstep.service.interfaces;

import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.Nurse;
import org.itstep.model.entity.Patient;
import java.util.List;

public interface INurseService extends GenericService <Nurse, SelectDTO> {
    List<Nurse> findByUsername(String username) throws ServiceExeption;

    List<Nurse> findNursesByPatientsIsNotContaining(long patientId) throws ServiceExeption;

    List<Nurse> findByPatientId(long patientId) throws ServiceExeption;
    boolean addPatientToNurse(long userId,long nurseId) throws ServiceExeption;
    boolean deletePatientToNurse(long userId,long nurseId) throws ServiceExeption;
}
