package org.itstep.service;

import org.itstep.dao.DaoFactory;
import org.itstep.dao.NurseDao;
import org.itstep.exeption.DaoExeption;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.Nurse;
import org.itstep.model.entity.Patient;
import org.itstep.service.interfaces.INurseService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class NurseService implements INurseService {
    Logger log = Logger.getLogger(NurseService.class.getName());
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<Nurse> findByUsername(String username) throws ServiceExeption {
        log.info("Start findByUsername " + username);
        DaoFactory factory = DaoFactory.getInstance();
        NurseDao dao = factory.createNurseDao();
        try {
            return dao.findByUsername(username);
        } catch (DaoExeption e) {
            log.info("findByUsername " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public List<Nurse> findNursesByPatientsIsNotContaining(long patientId) throws ServiceExeption {
        log.info("Start findNursesByPatientsIsNotContaining " + patientId);
        DaoFactory factory = DaoFactory.getInstance();
        NurseDao dao = factory.createNurseDao();
        try {
            return dao.findNursesByPatientsIsNotContaining(patientId);
        } catch (DaoExeption e) {
            log.info("findNursesByPatientsIsNotContaining " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public List<Nurse> findByPatientId(long patientId) throws ServiceExeption {
        log.info("Start findByPatients " + patientId);
        DaoFactory factory = DaoFactory.getInstance();
        NurseDao dao = factory.createNurseDao();
        try {
            return dao.findByPatientId(patientId);
        } catch (DaoExeption e) {
            log.info("findByPatients " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public boolean addPatientToNurse(long userId, long nurseId) throws ServiceExeption {
        log.info("Start addPatientToNurse " + userId + nurseId);
        DaoFactory factory = DaoFactory.getInstance();
        NurseDao dao = factory.createNurseDao();
        try {
            return dao.addPatientToNurse(userId, nurseId);
        } catch (DaoExeption e) {
            log.info("addPatientToNurse " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public boolean deletePatientToNurse(long userId, long nurseId) throws ServiceExeption {
        log.info("Start deletePatientToNurse " + userId + nurseId);
        DaoFactory factory = DaoFactory.getInstance();
        NurseDao dao = factory.createNurseDao();
        try {
            return dao.deletePatientToNurse(userId, nurseId);
        } catch (DaoExeption e) {
            log.info("deletePatientToNurse " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public List<Nurse> getAll(SelectDTO selectDTO) throws ServiceExeption {
        return null;
    }
}
