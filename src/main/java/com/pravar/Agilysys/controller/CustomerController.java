package com.pravar.Agilysys.controller;

import com.pravar.Agilysys.model.CloudService;
import com.pravar.Agilysys.model.Customer;
import com.pravar.Agilysys.service.CustomerService;
import com.pravar.Agilysys.validations.CustomerValidations;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Operation(summary = "Create Customer", description = "Onboard a new customer")
    @PostMapping("onboarding")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @Operation(summary = "Get All Customers", description = "Get a list of all customers")
    @GetMapping("allCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Get Customer by ID", description = "Get customer details by ID")
    @GetMapping("getCustomerByID/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerByID(@PathVariable Integer id) {
        return customerService.getCustomerByID(id);
    }

    @Operation(summary = "Get All Customers by Name", description = "Get a list of customers by name")
    @GetMapping("allCustomersByName/{name}")
    public ResponseEntity<List<Customer>> getAllCustomersByName(@PathVariable String name) {
        return customerService.getAllCustomersByName(name);
    }

    @Operation(summary = "Get Customer by Email", description = "Get customer details by email")
    @GetMapping("getCustomerByEmail/{email}")
    public ResponseEntity<Optional<Customer>> getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @Operation(summary = "Get All Customers by Onboarding Date", description = "Get a list of customers by onboarding date")
    @GetMapping("allCustomersByDate/{onboardingDate}")
    public ResponseEntity<List<Customer>> getAllCustomersByOnboardingDate(@PathVariable String onboardingDate) {
        return customerService.getAllCustomersByOnboardingDate(onboardingDate);
    }

    @Operation(summary = "Get All Customers by Service", description = "Get a list of customers by service name")
    @GetMapping("allCustomersByService/{serviceName}")
    public ResponseEntity<List<Customer>> getAllCustomersByService(@PathVariable String serviceName) {
        return customerService.getAllCustomersByService(serviceName);
    }

    @Operation(summary = "Delete Customer by ID", description = "Delete a customer by ID")
    @DeleteMapping("deleteCustomerByID/{id}")
    public ResponseEntity<String> deleteCustomerByID(@PathVariable Integer id) {
        return customerService.deleteCustomerByID(id);
    }

    @Operation(summary = "Delete Customer by Email", description = "Delete a customer by email")
    @DeleteMapping("deleteCustomerByEmail/{email}")
    public ResponseEntity<String> deleteCustomerByEmail(@PathVariable String email) {
        return customerService.deleteCustomerByEmail(email);
    }

    @Operation(summary = "Update Customer Details", description = "Update customer details by ID")
    @PutMapping("updateCustomer/{id}")
    public ResponseEntity<String> updateCustomerDetails(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomerDetails(id, updatedCustomer);
    }

    @Operation(summary = "Add Service to Customer", description = "Add a service to a customer by ID")
    @PostMapping("addService/{id}")
    public ResponseEntity<String> addService(@PathVariable Integer id, @RequestBody CloudService service) {
        return customerService.addService(id, service);
    }

    @Operation(summary = "Update Service Status", description = "Update service status by customer ID and service ID")
    @PutMapping("updateServiceStatus/{id}/{serviceID}")
    public ResponseEntity<String> updateServiceStatus(@PathVariable Integer id, @PathVariable Integer serviceID, @RequestParam boolean isPaused) {
        return customerService.updateServiceStatus(id, serviceID, isPaused);
    }
}
