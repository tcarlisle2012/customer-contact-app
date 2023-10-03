package com.gfs.gordonnow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A CustomerContact.
 */
@Table("customer_contact")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 50)
    @Column("first_name")
    private String firstName;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 50)
    @Column("middle_name")
    private String middleName;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 50)
    @Column("last_name")
    private String lastName;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 150)
    @Column("display_name")
    private String displayName;

    @NotNull(message = "must not be null")
    @Size(min = 5, max = 254)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column("email")
    private String email;

    @NotNull(message = "must not be null")
    @Size(min = 10, max = 10)
    @Column("phone")
    private String phone;

    @NotNull(message = "must not be null")
    @Size(min = 2, max = 120)
    @Column("department")
    private String department;

    @NotNull(message = "must not be null")
    @Size(min = 2, max = 120)
    @Column("job_title")
    private String jobTitle;

    @Transient
    @JsonIgnoreProperties(value = { "customerContacts" }, allowSetters = true)
    private CustomerUnitKey customerUnitKey;

    @Column("customer_unit_key_id")
    private Long customerUnitKeyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerContact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public CustomerContact firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public CustomerContact middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public CustomerContact lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public CustomerContact displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return this.email;
    }

    public CustomerContact email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public CustomerContact phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return this.department;
    }

    public CustomerContact department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public CustomerContact jobTitle(String jobTitle) {
        this.setJobTitle(jobTitle);
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public CustomerUnitKey getCustomerUnitKey() {
        return this.customerUnitKey;
    }

    public void setCustomerUnitKey(CustomerUnitKey customerUnitKey) {
        this.customerUnitKey = customerUnitKey;
        this.customerUnitKeyId = customerUnitKey != null ? customerUnitKey.getId() : null;
    }

    public CustomerContact customerUnitKey(CustomerUnitKey customerUnitKey) {
        this.setCustomerUnitKey(customerUnitKey);
        return this;
    }

    public Long getCustomerUnitKeyId() {
        return this.customerUnitKeyId;
    }

    public void setCustomerUnitKeyId(Long customerUnitKey) {
        this.customerUnitKeyId = customerUnitKey;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerContact)) {
            return false;
        }
        return id != null && id.equals(((CustomerContact) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerContact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", department='" + getDepartment() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            "}";
    }
}
