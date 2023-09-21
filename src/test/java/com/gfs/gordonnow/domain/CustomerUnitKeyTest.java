package com.gfs.gordonnow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gfs.gordonnow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerUnitKeyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerUnitKey.class);
        CustomerUnitKey customerUnitKey1 = new CustomerUnitKey();
        customerUnitKey1.setId(1L);
        CustomerUnitKey customerUnitKey2 = new CustomerUnitKey();
        customerUnitKey2.setId(customerUnitKey1.getId());
        assertThat(customerUnitKey1).isEqualTo(customerUnitKey2);
        customerUnitKey2.setId(2L);
        assertThat(customerUnitKey1).isNotEqualTo(customerUnitKey2);
        customerUnitKey1.setId(null);
        assertThat(customerUnitKey1).isNotEqualTo(customerUnitKey2);
    }
}
