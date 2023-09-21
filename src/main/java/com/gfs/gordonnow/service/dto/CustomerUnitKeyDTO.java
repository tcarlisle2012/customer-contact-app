package com.gfs.gordonnow.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gfs.gordonnow.domain.CustomerUnitKey} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerUnitKeyDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    private String customerNumber;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    private String salesOrganization;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    private String distributionChannel;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    private String division;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(String salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public String getDistributionChannel() {
        return distributionChannel;
    }

    public void setDistributionChannel(String distributionChannel) {
        this.distributionChannel = distributionChannel;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerUnitKeyDTO)) {
            return false;
        }

        CustomerUnitKeyDTO customerUnitKeyDTO = (CustomerUnitKeyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerUnitKeyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerUnitKeyDTO{" +
            "id=" + getId() +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", salesOrganization='" + getSalesOrganization() + "'" +
            ", distributionChannel='" + getDistributionChannel() + "'" +
            ", division='" + getDivision() + "'" +
            "}";
    }
}
