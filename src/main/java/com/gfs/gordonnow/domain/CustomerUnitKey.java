package com.gfs.gordonnow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A CustomerUnitKey.
 */
@Table("customer_unit_key")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerUnitKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    @Column("customer_number")
    private String customerNumber;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    @Column("sales_organization")
    private String salesOrganization;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    @Column("distribution_channel")
    private String distributionChannel;

    @NotNull(message = "must not be null")
    @Size(max = 16)
    @Column("division")
    private String division;

    @Transient
    @JsonIgnoreProperties(value = { "customerUnitKey" }, allowSetters = true)
    private Set<CustomerContact> customerContacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerUnitKey id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return this.customerNumber;
    }

    public CustomerUnitKey customerNumber(String customerNumber) {
        this.setCustomerNumber(customerNumber);
        return this;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSalesOrganization() {
        return this.salesOrganization;
    }

    public CustomerUnitKey salesOrganization(String salesOrganization) {
        this.setSalesOrganization(salesOrganization);
        return this;
    }

    public void setSalesOrganization(String salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public String getDistributionChannel() {
        return this.distributionChannel;
    }

    public CustomerUnitKey distributionChannel(String distributionChannel) {
        this.setDistributionChannel(distributionChannel);
        return this;
    }

    public void setDistributionChannel(String distributionChannel) {
        this.distributionChannel = distributionChannel;
    }

    public String getDivision() {
        return this.division;
    }

    public CustomerUnitKey division(String division) {
        this.setDivision(division);
        return this;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Set<CustomerContact> getCustomerContacts() {
        return this.customerContacts;
    }

    public void setCustomerContacts(Set<CustomerContact> customerContacts) {
        if (this.customerContacts != null) {
            this.customerContacts.forEach(i -> i.setCustomerUnitKey(null));
        }
        if (customerContacts != null) {
            customerContacts.forEach(i -> i.setCustomerUnitKey(this));
        }
        this.customerContacts = customerContacts;
    }

    public CustomerUnitKey customerContacts(Set<CustomerContact> customerContacts) {
        this.setCustomerContacts(customerContacts);
        return this;
    }

    public CustomerUnitKey addCustomerContact(CustomerContact customerContact) {
        this.customerContacts.add(customerContact);
        customerContact.setCustomerUnitKey(this);
        return this;
    }

    public CustomerUnitKey removeCustomerContact(CustomerContact customerContact) {
        this.customerContacts.remove(customerContact);
        customerContact.setCustomerUnitKey(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerUnitKey)) {
            return false;
        }
        return id != null && id.equals(((CustomerUnitKey) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerUnitKey{" +
            "id=" + getId() +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", salesOrganization='" + getSalesOrganization() + "'" +
            ", distributionChannel='" + getDistributionChannel() + "'" +
            ", division='" + getDivision() + "'" +
            "}";
    }
}
