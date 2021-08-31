package org.itstep.service.patient;

import org.itstep.dao.DaoFactory;
import org.itstep.dao.PatientDao;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.PatientDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.DaoExeption;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.Patient;
import org.itstep.model.entity.enums.Role;
import org.itstep.service.interfaces.IPatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PatientService implements IPatientService {
    Logger log = Logger.getLogger(PatientService.class.getName());
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<Patient> getAll(SelectDTO selectDTO) throws ServiceExeption {
        log.info("Start getListPatients of SelectDTO");
        DaoFactory factory = DaoFactory.getInstance();
        PatientDao dao = factory.createPatientDao();
        try {
            return dao.findAll(selectDTO);
        } catch (DaoExeption e) {
            log.info("getListPatients " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Patient> save(PatientDTO patientDTO) throws ServiceExeption {
        log.info("Start savePatient of User. userDTO = " + patientDTO);
        DaoFactory factory = DaoFactory.getInstance();
        PatientDao dao = factory.createPatientDao();
        Patient patient;
        try {
            patient = convertToEntity(patientDTO);
            dao.create(patient);
        } catch (DaoExeption | DateTimeParseException e) {
            log.info("savePatient " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
        return Optional.of(patient);
    }

    public Optional<Patient> update(PatientDTO patientDTO) throws ServiceExeption {
        log.info("Start update Patient of User. patientDTO = " + patientDTO);
        DaoFactory factory = DaoFactory.getInstance();
        PatientDao dao = factory.createPatientDao();
        Patient patient;
        try {
            patient = convertToEntity(patientDTO);
            dao.update(patient);
        } catch (DaoExeption | DateTimeParseException e) {
            //     log.info("savePatient {}, {}", env.getProperty("SAVE_NEW_PATIENT"), e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        } /*catch (DataIntegrityViolationException e) {
            log.error("savePatient {}, {}, {}", env.getProperty("SAVE_NEW_PATIENT_DUPLICATE"), patient, e.getMessage());
            throw new ServiceExeption(env.getProperty("SAVE_NEW_PATIENT_DUPLICATE"), e);
        }*/
        return Optional.of(patient);
    }


    @Override
    public Optional<PatientDTO> getPatientById(long id) throws ServiceExeption {
        log.info("Start getPatientById of User. id =  " + id);
        DaoFactory factory = DaoFactory.getInstance();
        PatientDao dao = factory.createPatientDao();
        try {
            return convertToDto(dao.findById(id));
        } catch (DaoExeption e) {
            log.info("findAllPatientsByNameDoctor " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }
/*
    public Page<Patient> getAllByNursesIsContaining(SelectDTO selectDTO, Pageable pageable) throws ServiceExeption {
        log.debug("Start getAllByNursesIsContaining of SelectDTO");
        try {
            return patientJPARepository.findAllByNursename(selectDTO.getUserNameDoctor(), pageable);
        } catch (DaoExeption | DataIntegrityViolationException e) {
            log.error("getAllByNursesIsContaining {}, {}", env.getProperty("GET_ALL_ERROR_MESSAGE_PATIENT"), e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public Page<Patient> getAll(Pageable pageable) throws ServiceExeption {
        log.debug("Start getListPatients");
        SelectDTO selectDTO =  SelectDTO.builder().authorities(new ArrayList<>(Collections.singletonList(Role.PATIENT))).build();
        try {
            return patientJPARepository.findAll(patientSpecification.getUsers(selectDTO), pageable);
        } catch (DaoExeption | DataIntegrityViolationException e) {
            log.error("getListPatients {}, {}", env.getProperty("GET_ALL_ERROR_MESSAGE_PATIENT"), e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }





    @Override
    public Page<Patient> findAllCurrentPatientsByNameDoctor(SelectDTO selectDTO,Pageable pageable) throws ServiceExeption {
        log.debug("Start findAllCurrentPatientsByNameDoctor of User. selectDTO = {}", selectDTO);
        try {
            return patientJPARepository.findAll(patientSpecification.getCurrentPatientsByDoctorName(selectDTO), pageable);
        } catch (DaoExeption | DataIntegrityViolationException e) {
            log.error("findAllPatientsByNameDoctor {}, {}", env.getProperty("GET_ALL_ERROR_MESSAGE_DOCTOR"), e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    public PatientNurse addNurseById(String nurseId, String userId) throws ServiceExeption {
        log.debug("Start addNurseById of User. id = {}", userId);
        try {
            return parientNurseJPARepository.save(PatientNurse.builder()
                    .nurses_id(Long.valueOf(nurseId))
                    .patients_user_id(Long.valueOf(userId))
                    .build());
        } catch (DaoExeption | DataIntegrityViolationException e) {
            log.error("addNurseById {}, {}", env.getProperty("GET_ALL_ERROR_MESSAGE_DOCTOR"), e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    public int deleteNurseById(String nurseId, String userId) throws ServiceExeption {
        log.debug("Start deleteNurseById of User. id = {}", nurseId);
        try {
            return parientNurseJPARepository.deleteNurse(
                    Long.valueOf(nurseId), Long.valueOf(userId));
        } catch (DaoExeption | DataIntegrityViolationException e) {
            log.error("deleteNurseById {}, {}", env.getProperty("GET_ALL_ERROR_MESSAGE_DOCTOR"), e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

*/

    public Optional<PatientDTO> convertToDto(Optional<Patient> convertToDto) {
        return convertToDto.map(this::convertToDto);
    }

    public List<PatientDTO> convertToDto(List<Patient> patients) {
        return patients
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PatientDTO convertToDto(Patient patient) {
        return PatientDTO
                .newBuilder()
                .setId(String.valueOf(patient.getId()))
                .setActualPatient(patient.isIscurrentpatient())
                .setAuthorities(Collections.singletonList(patient.getRole()))
                .setBirthDate(patient.getBirthDate().format(df))
                .setUsername(patient.getUsername())
                .setDoctorDTO(DoctorDTO.newBuilder()
                        .setId(patient.getDoctor().getId())
                        .setUsername(patient.getDoctor().getUsername())
                        .build())
                .build();
    }

    public Patient convertToEntity(PatientDTO patientDTO) throws DateTimeParseException {
        return Patient.newBuilder()
                .setId(patientDTO.getId() !=null ? Long.parseLong(patientDTO.getId()) : null)
                .setPassword(patientDTO.getPassword())
                .setUsername(patientDTO.getUsername())
                .setRole(Role.PATIENT)
                .setDoctor(patientDTO.getDoctorDTO() != null ? Doctor.newBuilder().setId(patientDTO.getDoctorDTO().getId()).build() : null)
                .setIscurrentpatient(patientDTO.getActualPatient())
                .setBirthDate(LocalDate.parse(patientDTO.getBirthDate(), df))
                .build();
    }
}
