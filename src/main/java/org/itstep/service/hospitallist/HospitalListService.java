package org.itstep.service.hospitallist;

import org.itstep.dao.DaoFactory;
import org.itstep.dao.DoctorDao;
import org.itstep.dao.HospitalListDao;
import org.itstep.exeption.DaoExeption;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.enums.Role;
import org.itstep.service.interfaces.IDoctorService;
import org.itstep.service.interfaces.IHospitalLisService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HospitalListService implements IHospitalLisService {
    Logger log = Logger.getLogger(HospitalListService.class.getName());


    @Override
   public Optional<HospitalList> findByParientIdAndDoctorName(int userId, String userNameDoctor) throws ServiceExeption
    {        log.info("Start findByParientIdAndDoctorName");
        DaoFactory factory = DaoFactory.getInstance();
        HospitalListDao dao = factory.createHospitalListDao();
        try {
            return dao.findByParientIdAndDoctorName(userId, userNameDoctor);
        } catch (DaoExeption e) {
            log.info("error findByParientIdAndDoctorName " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }
    @Override
    public void save(HospitalList hospitalList) throws ServiceExeption{
        log.info("Start save");
        DaoFactory factory = DaoFactory.getInstance();
        HospitalListDao dao = factory.createHospitalListDao();
        try {
            dao.create(hospitalList);
        } catch (DaoExeption e) {
            log.info("findByParientIdAndDoctorName " + e.getMessage());
            throw new ServiceExeption(e.getMessage(), e);
        }
    }

    @Override
    public List<HospitalList> getAll(SelectDTO selectDTO) throws ServiceExeption {
        return null;
    }
}
