package com.gfs.gordonnow.service.mapper;

import com.gfs.gordonnow.domain.CustomerUnitKey;
import com.gfs.gordonnow.service.dto.CustomerUnitKeyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerUnitKey} and its DTO {@link CustomerUnitKeyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerUnitKeyMapper extends EntityMapper<CustomerUnitKeyDTO, CustomerUnitKey> {}
