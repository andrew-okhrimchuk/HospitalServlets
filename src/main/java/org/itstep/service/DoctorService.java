package org.itstep.service;

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

    public Doctor convertToEntity(DoctorDTO patientDTO) {
        return Doctor.newBuilder()
                .setUsername(patientDTO.getUsername())
                .setPassword(patientDTO.getPassword())
                .setSpeciality(patientDTO.getSpeciality())
                .build();
    }

    public DoctorDTO convertToDto(Doctor doctor) {
        return DoctorDTO.newBuilder()
                .setId(doctor.getId())
                .setUsername(doctor.getUsername())
                .setPassword(doctor.getPassword())
                .setSpeciality(doctor.getSpeciality())
                .setCountOfPatients(doctor.getCountOfPatients())
                .build();
    }
}
