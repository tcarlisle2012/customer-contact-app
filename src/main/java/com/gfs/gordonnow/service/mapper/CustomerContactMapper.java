package com.gfs.gordonnow.service.mapper;

import com.gfs.gordonnow.domain.CustomerContact;
import com.gfs.gordonnow.domain.CustomerUnitKey;
import com.gfs.gordonnow.service.dto.CustomerContactDTO;
import com.gfs.gordonnow.service.dto.CustomerUnitKeyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerContact} and its DTO {@link CustomerContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerContactMapper extends EntityMapper<CustomerContactDTO, CustomerContact> {
    @Mapping(target = "customerUnitKey", source = "customerUnitKey", qualifiedByName = "customerUnitKeyCustomerNumber")
    CustomerContactDTO toDto(CustomerContact s);

    @Named("customerUnitKeyCustomerNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerNumber", source = "customerNumber")
    CustomerUnitKeyDTO toDtoCustomerUnitKeyCustomerNumber(CustomerUnitKey customerUnitKey);
}
