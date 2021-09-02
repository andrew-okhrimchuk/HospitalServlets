package org.itstep.service.interfaces;

import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.MedicationLog;

import java.util.List;
import java.util.Optional;

public interface IMedicationLogService extends GenericService<MedicationLog, SelectDTO> {
    void save(MedicationLog hospitalList) throws ServiceExeption;

    void update(MedicationLog hospitalList) throws ServiceExeption;

    List<MedicationLog> findByParientId(long userId) throws ServiceExeption;

}
