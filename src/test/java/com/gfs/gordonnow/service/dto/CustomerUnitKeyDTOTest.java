package com.gfs.gordonnow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gfs.gordonnow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerUnitKeyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerUnitKeyDTO.class);
        CustomerUnitKeyDTO customerUnitKeyDTO1 = new CustomerUnitKeyDTO();
        customerUnitKeyDTO1.setId(1L);
        CustomerUnitKeyDTO customerUnitKeyDTO2 = new CustomerUnitKeyDTO();
        assertThat(customerUnitKeyDTO1).isNotEqualTo(customerUnitKeyDTO2);
        customerUnitKeyDTO2.setId(customerUnitKeyDTO1.getId());
        assertThat(customerUnitKeyDTO1).isEqualTo(customerUnitKeyDTO2);
        customerUnitKeyDTO2.setId(2L);
        assertThat(customerUnitKeyDTO1).isNotEqualTo(customerUnitKeyDTO2);
        customerUnitKeyDTO1.setId(null);
        assertThat(customerUnitKeyDTO1).isNotEqualTo(customerUnitKeyDTO2);
    }
}
