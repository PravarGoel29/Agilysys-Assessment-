package com.pravar.Agilysys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pravar.Agilysys.model.Customer;
import com.pravar.Agilysys.model.CloudService;
import com.pravar.Agilysys.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setAddress("Test Address");
        customer.setCloudServices(new ArrayList<>());
        customerRepository.save(customer);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    @Test
    void createCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Pravar");
        customer.setEmail("pravargoel29@gmail.com");
        customer.setAddress("1207 Willow Ave, Hoboken");
        customer.setOnboardingDate(LocalDate.now().format(formatter).toString());
        customer.setCloudServices(new ArrayList<>());

        String json = objectMapper.writeValueAsString(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/onboarding")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success: New customer added"));
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/allCustomers"))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/getCustomerByID/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCustomersByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/allCustomersByName/Pravar"))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/getCustomerByEmail/pravargoel29@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCustomersByOnboardingDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/allCustomersByDate/01-24-2024"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/deleteCustomerByID/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success: Customer deleted"));
    }

    @Test
    void deleteCustomerByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/deleteCustomerByEmail/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success: Customer deleted"));
    }

    @Test
    void updateCustomerDetails() throws Exception {
        Customer updatedCustomer = new Customer();
        Customer customer = new Customer();

        customer.setId(1);
        customer.setName("Pravar");
        customer.setEmail("pravargoel29@gmail.com");
        customer.setAddress("1207 Willow Ave, Hoboken");
        customer.setOnboardingDate(LocalDate.now().format(formatter).toString());
        customer.setCloudServices(new ArrayList<>());

        updatedCustomer.setName("NewName");
        updatedCustomer.setEmail("newemail@example.com");
        updatedCustomer.setAddress("New Address");
        updatedCustomer.setCloudServices(new ArrayList<>());

        String json = objectMapper.writeValueAsString(updatedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put("/customer/updateCustomer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void addService() throws Exception {
        CloudService cloudService = new CloudService();
        cloudService.setServiceName("S3");
        cloudService.setPlan("Standard Storage");

        String json = objectMapper.writeValueAsString(cloudService);

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/addService/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updateServiceStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/updateServiceStatus/1/1?isPaused=false"))
                .andExpect(status().isOk());
    }
}
