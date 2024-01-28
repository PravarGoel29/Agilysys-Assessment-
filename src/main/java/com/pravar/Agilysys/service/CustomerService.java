package com.pravar.Agilysys.service;

import com.pravar.Agilysys.model.CloudService;
import com.pravar.Agilysys.model.Customer;
import com.pravar.Agilysys.repository.CloudServiceRepository;
import com.pravar.Agilysys.repository.CustomerRepository;
import com.pravar.Agilysys.validations.CloudServiceValidations;
import com.pravar.Agilysys.validations.CustomerValidations;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CloudServiceRepository cloudServiceRepository;

    @Autowired
    CustomerValidations customerValidations;

    @Autowired
    Customer customer;

    @Autowired
    CloudServiceValidations cloudServiceValidations;

    public ResponseEntity<String> createCustomer(Customer customer) {
        try{
            if(!customerValidations.isNameValid(customer)) {
                return new ResponseEntity<>("Failure: Name field cannot be empty or have numbers or special characters", HttpStatus.BAD_REQUEST);
            }
            if(!customerValidations.isEmailValid(customer)) {
                return new ResponseEntity<>("Failure: Email field either empty or in incorrect format", HttpStatus.BAD_REQUEST);
            }
            if(!customerValidations.isAddressValid(customer)) {
                return new ResponseEntity<>("Failure: Address field cannot be empty", HttpStatus.BAD_REQUEST);
            }

            String email = customer.getEmail().toLowerCase();
            customer.setEmail(email);

            List<CloudService> cloudServices = customer.getCloudServices();

            for (CloudService service : cloudServices) {
                if(!cloudServiceValidations.isServiceNameValid(service)) {
                    return new ResponseEntity<>("Failure: Cloud Service not found", HttpStatus.BAD_REQUEST);
                }
                if(!cloudServiceValidations.isPlanValid(service)) {
                    return new ResponseEntity<>("Failure: No such plan found for " + service.getServiceName() + " service", HttpStatus.BAD_REQUEST);
                }
            }

            Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
            if(existingCustomer.isPresent()) {
                return new ResponseEntity<>("Failure: Provided email already exists", HttpStatus.BAD_REQUEST);
            }

            customerRepository.save(customer);

            return new ResponseEntity<>("Success: New customer added", HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        try{
            return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Optional<Customer>> getCustomerByID(Integer id) {
        try{
            return new ResponseEntity<>(customerRepository.findById(id), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Customer>> getAllCustomersByName(String name) {
        try{
            return new ResponseEntity<>(customerRepository.findByName(name), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Optional<Customer>> getCustomerByEmail(String email) {
        try{
            return new ResponseEntity<>(customerRepository.findByEmail(email), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Customer>> getAllCustomersByOnboardingDate(String onboardingDate) {
        try{
            return new ResponseEntity<>(customerRepository.findByOnboardingDate(onboardingDate), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Customer>> getAllCustomersByService(String serviceName) {
        try {
            List<Customer> customers = customerRepository.findByServiceName(serviceName);

            if (customers.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteCustomerByID(Integer id) {
        try {
            Optional<Customer> existingCustomer = customerRepository.findById(id);

            if (existingCustomer.isPresent()) {
                customerRepository.deleteById(id);
                return new ResponseEntity<>("Success: Customer deleted", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Failure: Customer not found", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure: An error occurred while deleting the customer", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<String> deleteCustomerByEmail(String email) {
        try {
            Optional<Customer> existingCustomer = customerRepository.findByEmail(email);

            if (existingCustomer.isPresent()) {
                customerRepository.deleteByEmail(email);
                return new ResponseEntity<>("Success: Customer deleted", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Failure: Customer not found", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure: An error occurred while deleting the customer", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateCustomerDetails(Integer id, Customer updatedCustomer) {
        try {
            if(!customerValidations.isNameValid(updatedCustomer)) {
                return new ResponseEntity<>("Failure: Name field cannot be empty or have numbers or special characters", HttpStatus.BAD_REQUEST);
            }
            if(!customerValidations.isEmailValid(updatedCustomer)) {
                return new ResponseEntity<>("Failure: Email field either empty or in incorrect format", HttpStatus.BAD_REQUEST);
            }
            if(!customerValidations.isAddressValid(updatedCustomer)) {
                return new ResponseEntity<>("Failure: Address field cannot be empty", HttpStatus.BAD_REQUEST);
            }
            Optional<Customer> existingCustomer = customerRepository.findById(id);

                if(customerValidations.exactlyMatches(existingCustomer.get(),updatedCustomer)) {
                    return new ResponseEntity<>("Info: No changes detected in customer details. Existing details remain unchanged.", HttpStatus.OK);
                }


            if (existingCustomer.isPresent()) {
                Customer customerToUpdate = existingCustomer.get();

                customerToUpdate.setName(updatedCustomer.getName());
                customerToUpdate.setEmail(updatedCustomer.getEmail());
                customerToUpdate.setAddress(updatedCustomer.getAddress());
                customerToUpdate.setOnboardingDate(customerToUpdate.getOnboardingDate());

                customerRepository.save(customerToUpdate);

                return new ResponseEntity<>("Success: Customer details updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failure: Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure: An error occurred while updating customer details", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> addService(Integer id, CloudService service) {
        try {
            if(!cloudServiceValidations.isServiceNameValid(service)) {
                return new ResponseEntity<>("Failure: Cloud Service not found", HttpStatus.BAD_REQUEST);
            }
            if(!cloudServiceValidations.isPlanValid(service)) {
                return new ResponseEntity<>("Failure: No such plan found for " + service.getServiceName() + " service", HttpStatus.BAD_REQUEST);
            }

            Optional<Customer> existingCustomer = customerRepository.findById(id);

            if (existingCustomer.isPresent()) {
                Customer customer = existingCustomer.get();
                List<CloudService> cloudServices = customer.getCloudServices();

                for (CloudService existingServices : cloudServices) {
                    if (existingServices.getServiceName().equals(service.getServiceName())) {
                        return updateServiceStatus(id, existingServices.getServiceID(),false);
                    }
                }
                cloudServices.add(service);
                cloudServiceRepository.saveAll(cloudServices);
                customerRepository.save(customer);

                return new ResponseEntity<>("Success: New Service added", HttpStatus.OK);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure: An error occurred while adding service", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateServiceStatus(Integer id, Integer serviceID, boolean isPaused) {
        try {
            Optional<Customer> existingCustomer = customerRepository.findById(id);

            if (existingCustomer.isPresent()) {
                Customer customer = existingCustomer.get();
                List<CloudService> cloudServices = customer.getCloudServices();

                for (CloudService service : cloudServices) {
                    if (service.getServiceID().equals(serviceID)) {
                        if(service.isPaused() == isPaused) {
                            return new ResponseEntity<>("Info: No changes made to the service status", HttpStatus.OK);
                        }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                        if (!isPaused) {
                            service.setServiceStartDate(LocalDate.now().format(formatter).toString());
                        }
                        else {
                            service.setServicePauseDate(LocalDate.now().format(formatter).toString());
                        }
                        service.setPaused(isPaused);
                        break;
                    }
                }
                customerRepository.save(customer);
                return new ResponseEntity<>("Success: Service status updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failure: Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure: An error occurred while updating service status", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
