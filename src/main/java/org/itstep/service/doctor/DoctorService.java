package org.itstep.service.doctor;

import org.itstep.dao.DaoFactory;
import org.itstep.dao.DoctorDao;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.DaoExeption;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.enums.Role;
import org.itstep.service.interfaces.IDoctorService;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DoctorService implements IDoctorService {
    Logger log = Logger.getLogger(DoctorService.class.getName());


    @Override
    public List<Doctor> getAll(SelectDTO selectDTO) throws ServiceExeption {
        log.info("Start getAll of SelectDTO");
        DaoFactory factory = DaoFactory.getInstance();
        DoctorDao dao = factory.createDoctorDao();
        try {
            return dao.findAll(selectDTO);
        } catch (DaoExeption e) {
            log.info("getAll " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public List<DoctorDTO> findAllWithCount() throws ServiceExeption {
        log.info("Start findAllWithCount");
        DaoFactory factory = DaoFactory.getInstance();
        DoctorDao dao = factory.createDoctorDao();
        try {
            return dao.findAllWithCount()
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (DaoExeption e) {
            log.info("findAllWithCount " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public List<DoctorDTO> getAll() throws ServiceExeption {
        log.info("Start getListPatients ");
        DaoFactory factory = DaoFactory.getInstance();
        DoctorDao dao = factory.createDoctorDao();
        try {
            return dao.findAll()
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (DaoExeption e) {
            log.info("getListPatients " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    public List<DoctorDTO> getAllDoctorDTO(SelectDTO selectDTO) throws ServiceExeption {
        log.info("Start getAllDoctorDTO of SelectDTO");
        return getAll(selectDTO)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Doctor save(DoctorDTO patientDTO) throws ServiceExeption {
        log.info("Start save Doctor of User. userDTO = " + patientDTO);
        Doctor user = null;
        try {
            user = convertToEntity(patientDTO);
            user.setrole(Role.DOCTOR);
            DaoFactory factory = DaoFactory.getInstance();
            DoctorDao dao = factory.createDoctorDao();
            dao.create(user);
            return user;
        } catch (DaoExeption e) {
            log.info("savePatient " + e.getMessage());
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
    public PatientDTO getPatientById(long id) throws ServiceExeption {
        log.debug("Start getPatientById of User. id = {}", id);

        try {
        return convertToDto(patientJPARepository.getPatientById(id));
        } catch (DaoExeption | DataIntegrityViolationException e) {
            log.error("findAllPatientsByNameDoctor {}, {}", env.getProperty("GET_ERROR_MESSAGE_PATIENT"), e.getMessage());
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
    }*/

    public Doctor convertToEntity(DoctorDTO patientDTO) {
        return Doctor.newBuilder()
                .setUsername(patientDTO.getUsername())
                .setPassword(patientDTO.getPassword())
                .setSpeciality(patientDTO.getSpeciality())
                .build();
    }

    public DoctorDTO convertToDto(Doctor doctor) {
        return DoctorDTO.newBuilder()
                .setUsername(doctor.getUsername())
                .setPassword(doctor.getPassword())
                .setSpeciality(doctor.getSpeciality())
                .setCountOfPatients(doctor.getCountOfPatients())
                .build();
    }
}
