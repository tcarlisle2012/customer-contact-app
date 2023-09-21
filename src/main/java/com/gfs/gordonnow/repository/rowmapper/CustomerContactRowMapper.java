package com.gfs.gordonnow.repository.rowmapper;

import com.gfs.gordonnow.domain.CustomerContact;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link CustomerContact}, with proper type conversions.
 */
@Service
public class CustomerContactRowMapper implements BiFunction<Row, String, CustomerContact> {

    private final ColumnConverter converter;

    public CustomerContactRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link CustomerContact} stored in the database.
     */
    @Override
    public CustomerContact apply(Row row, String prefix) {
        CustomerContact entity = new CustomerContact();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFirstName(converter.fromRow(row, prefix + "_first_name", String.class));
        entity.setLastName(converter.fromRow(row, prefix + "_last_name", String.class));
        entity.setDisplayName(converter.fromRow(row, prefix + "_display_name", String.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setPhoneNumber(converter.fromRow(row, prefix + "_phone_number", String.class));
        entity.setCustomerUnitKeyId(converter.fromRow(row, prefix + "_customer_unit_key_id", Long.class));
        return entity;
    }
}
