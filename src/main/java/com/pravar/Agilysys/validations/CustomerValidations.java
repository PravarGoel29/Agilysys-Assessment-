package com.pravar.Agilysys.validations;

import com.pravar.Agilysys.model.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Component
public class CustomerValidations {

    @Autowired
    Customer customer;

    public boolean isNameValid(@NotNull Customer customer) {
        if (customer.getName() == null) {
            return false;
        }
        String nameRegex = "^[a-zA-Z\\s]+$";
        if (!customer.getName().matches(nameRegex)) {
            return false;
        }
        return true;
    }

    public boolean isEmailValid(@NotNull Customer customer) {

        if (customer.getEmail() == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!customer.getEmail().matches(emailRegex)) {
            return false;
        }
        return true;
    }

    public boolean isAddressValid(@NotNull Customer customer) {
        if (customer.getAddress().equals("")) {
            return false;
        }
        return true;
    }

    public boolean exactlyMatches(Customer existingCustomer, Customer updatedCustomer) {

        if (existingCustomer == updatedCustomer) return true;

        if (existingCustomer == null || updatedCustomer == null || existingCustomer.getClass() != updatedCustomer.getClass()) {
            return false;
        }

        return Objects.equals(existingCustomer.getName(), updatedCustomer.getName()) &&
                Objects.equals(existingCustomer.getEmail(), updatedCustomer.getEmail()) &&
                Objects.equals(existingCustomer.getAddress(), updatedCustomer.getAddress());
    }
}
