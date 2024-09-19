package com.mkappworks.ecommerce.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerMapper customerMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Address address;
    private Customer customerWithId;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .houseNumber("1")
                .street("street")
                .zipCode("123").
                build();

        customerWithId = Customer.builder()
                .id("1")
                .firstName("John")
                .lastName("Deer")
                .email("john.deer@email.com")
                .address(address)
                .build();
    }

    @Test
    void should_create_a_customer() throws Exception {
        var customerRequest = new CustomerRequest("1", "John", "Deer", "john.deer@email.com", address);
        var customerRequestJson = objectMapper.writeValueAsString(customerRequest);
        var customerResponse = "1";

        when(customerMapper.toCustomer(any(CustomerRequest.class))).thenReturn(customerWithId);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customerResponse);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType("application/json")
                        .content(customerRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("1"));

        verify(customerMapper, times(1)).toCustomer(any(CustomerRequest.class));
        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void should_update_a_customer() throws Exception {
        var customerRequest = new CustomerRequest("1", "John", "Deer", "john.deer@email.com", address);
        var customerRequestJson = objectMapper.writeValueAsString(customerRequest);

        when(customerMapper.toCustomer(any(CustomerRequest.class))).thenReturn(customerWithId);

        mockMvc.perform(put("/api/v1/customer")
                        .contentType("application/json")
                        .content(customerRequestJson))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));

        verify(customerMapper, times(1)).toCustomer(any(CustomerRequest.class));
        verify(customerService, times(1)).updateCustomer(any(Customer.class));
    }

    @Test
    void should_find_all_customers() throws Exception {
        var customerResponse = new CustomerResponse("1", "John", "Deer", "john.deer@email.com", address);
        var customerResponses = List.of(customerWithId);
        var customerResponsesJson = objectMapper.writeValueAsString(customerResponses);

        when(customerService.getCustomers()).thenReturn(customerResponses);
        when(customerMapper.fromCustomer(any(Customer.class))).thenReturn(customerResponse);

        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(status().isOk())
                .andExpect(content().json(customerResponsesJson));

        verify(customerMapper, times(1)).fromCustomer(any(Customer.class));
        verify(customerService, times(1)).getCustomers();
    }

    @Test
    void should_get_whether_customer_exists_by() throws Exception {
        when(customerService.existById(any())).thenReturn(true);

        mockMvc.perform(get("/api/v1/customer/exists/{id}", customerWithId.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(customerService, times(1)).existById(any());
    }

    @Test
    void should_find_customer_by_id() throws Exception {
        var customerResponse = new CustomerResponse("1", "John", "Deer", "john.deer@email.com", address);
        var customerResponseJson = objectMapper.writeValueAsString(customerResponse);

        when(customerService.findCustomerById(any())).thenReturn(customerWithId);
        when(customerMapper.fromCustomer(any(Customer.class))).thenReturn(customerResponse);

        mockMvc.perform(get("/api/v1/customer/{id}", customerWithId.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(customerResponseJson));

        verify(customerMapper, times(1)).fromCustomer(any(Customer.class));
        verify(customerService, times(1)).findCustomerById(any());
    }


    @Test
    void should_be_able_delete_a_customer_by_id() throws Exception {
        when(customerService.existById(any())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/customer/{id}", customerWithId.getId()))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));

        verify(customerService, times(1)).deleteCustomer(any());
    }
}
