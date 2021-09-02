package org.itstep.service.interfaces;

import org.itstep.model.dto.PatientDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService extends GenericService <Patient, SelectDTO> {

    List<Patient> getAll(SelectDTO selectDTO) throws ServiceExeption;
    Optional<Patient> save(PatientDTO patientDTO) throws ServiceExeption;
    Optional<PatientDTO> getPatientById(long id) throws ServiceExeption;
}
