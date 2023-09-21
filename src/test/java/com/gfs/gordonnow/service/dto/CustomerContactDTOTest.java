package com.gfs.gordonnow.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gfs.gordonnow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerContactDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerContactDTO.class);
        CustomerContactDTO customerContactDTO1 = new CustomerContactDTO();
        customerContactDTO1.setId(1L);
        CustomerContactDTO customerContactDTO2 = new CustomerContactDTO();
        assertThat(customerContactDTO1).isNotEqualTo(customerContactDTO2);
        customerContactDTO2.setId(customerContactDTO1.getId());
        assertThat(customerContactDTO1).isEqualTo(customerContactDTO2);
        customerContactDTO2.setId(2L);
        assertThat(customerContactDTO1).isNotEqualTo(customerContactDTO2);
        customerContactDTO1.setId(null);
        assertThat(customerContactDTO1).isNotEqualTo(customerContactDTO2);
    }
}
