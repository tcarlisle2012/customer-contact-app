package com.gfs.gordonnow.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.gfs.gordonnow.domain.CustomerContact} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerContactDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 150)
    private String displayName;

    @NotNull(message = "must not be null")
    @Size(min = 5, max = 254)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

    @NotNull(message = "must not be null")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    private CustomerUnitKeyDTO customerUnitKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerUnitKeyDTO getCustomerUnitKey() {
        return customerUnitKey;
    }

    public void setCustomerUnitKey(CustomerUnitKeyDTO customerUnitKey) {
        this.customerUnitKey = customerUnitKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerContactDTO)) {
            return false;
        }

        CustomerContactDTO customerContactDTO = (CustomerContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerContactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerContactDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", customerUnitKey=" + getCustomerUnitKey() +
            "}";
    }
}
