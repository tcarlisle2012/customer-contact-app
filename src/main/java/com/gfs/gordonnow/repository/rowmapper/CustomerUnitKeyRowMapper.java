package com.gfs.gordonnow.repository.rowmapper;

import com.gfs.gordonnow.domain.CustomerUnitKey;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link CustomerUnitKey}, with proper type conversions.
 */
@Service
public class CustomerUnitKeyRowMapper implements BiFunction<Row, String, CustomerUnitKey> {

    private final ColumnConverter converter;

    public CustomerUnitKeyRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link CustomerUnitKey} stored in the database.
     */
    @Override
    public CustomerUnitKey apply(Row row, String prefix) {
        CustomerUnitKey entity = new CustomerUnitKey();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCustomerNumber(converter.fromRow(row, prefix + "_customer_number", String.class));
        entity.setSalesOrganization(converter.fromRow(row, prefix + "_sales_organization", String.class));
        entity.setDistributionChannel(converter.fromRow(row, prefix + "_distribution_channel", String.class));
        entity.setDivision(converter.fromRow(row, prefix + "_division", String.class));
        return entity;
    }
}
