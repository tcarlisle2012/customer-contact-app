package com.gfs.gordonnow.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerUnitKeyMapperTest {

    private CustomerUnitKeyMapper customerUnitKeyMapper;

    @BeforeEach
    public void setUp() {
        customerUnitKeyMapper = new CustomerUnitKeyMapperImpl();
    }
}
