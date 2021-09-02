package org.itstep.service;

import org.itstep.dao.DaoFactory;
import org.itstep.dao.HospitalListDao;
import org.itstep.dao.MedicationLogDao;
import org.itstep.exeption.DaoExeption;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.MedicationLog;
import org.itstep.service.interfaces.IHospitalLisService;
import org.itstep.service.interfaces.IMedicationLogService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MedicationLogService implements IMedicationLogService {
    Logger log = Logger.getLogger(MedicationLogService.class.getName());


    @Override
    public List<MedicationLog> findByParientId(long userId) throws ServiceExeption {
        log.info("Start findByParientIdAndDoctorName");
        DaoFactory factory = DaoFactory.getInstance();
        MedicationLogDao dao = factory.createMedicationLogDao();
        try {
            return dao.findByPatientId(userId);
        } catch (DaoExeption e) {
            log.info("error findByParientIdAndDoctorName " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public void save(MedicationLog medicationLog) throws ServiceExeption {
        log.info("Start save");
        DaoFactory factory = DaoFactory.getInstance();
        MedicationLogDao dao = factory.createMedicationLogDao();
        try {
            if (medicationLog.getId() == null) {
                dao.create(medicationLog);
            } else {
                dao.update(medicationLog);
            }
        } catch (DaoExeption e) {
            log.info("findByParientIdAndDoctorName " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public void update(MedicationLog hospitalList) throws ServiceExeption {

    }

    @Override
    public List<MedicationLog> getAll(SelectDTO selectDTO) throws ServiceExeption {
        return null;
    }
}
