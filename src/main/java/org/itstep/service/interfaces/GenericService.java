package org.itstep.service.interfaces;

import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.ServiceExeption;

import java.util.List;

public interface GenericService<T, V> {
    List<T> getAll(SelectDTO selectDTO)throws ServiceExeption;
}
