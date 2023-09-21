package com.gfs.gordonnow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gfs.gordonnow.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerContact.class);
        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setId(1L);
        CustomerContact customerContact2 = new CustomerContact();
        customerContact2.setId(customerContact1.getId());
        assertThat(customerContact1).isEqualTo(customerContact2);
        customerContact2.setId(2L);
        assertThat(customerContact1).isNotEqualTo(customerContact2);
        customerContact1.setId(null);
        assertThat(customerContact1).isNotEqualTo(customerContact2);
    }
}
