package com.gfs.gordonnow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerContactMapperTest {

    private CustomerContactMapper customerContactMapper;

    @BeforeEach
    public void setUp() {
        customerContactMapper = new CustomerContactMapperImpl();
    }
}
