package com.mkappworks.ecommerce.cutomer;

import com.mkappworks.ecommerce.customer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerMapperTests {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = new CustomerMapper();
    }

    @Test
    public void shouldMapCustomerRequestToCustomerAndReturn() {
        var address = Address.builder()
                .houseNumber("1")
                .street("street")
                .zipCode("123").
                build();
        var customerRequest = new CustomerRequest("1",
                "John", "Deer", "john.deer@email.com", address);
        var expectedCustomer = Customer.builder()
                .id("1")
                .firstName("John")
                .lastName("Deer")
                .email("john.deer@email.com")
                .address(address)
                .build();

        var mappedCustomer = customerMapper.toCustomer(customerRequest);

        assertEquals(expectedCustomer, mappedCustomer);
    }

    @Test
    public void shouldReturnNullIfCustomerRequestIsNull() {
        var mappedCustomer = new CustomerMapper().toCustomer(null);

        assertEquals(null, mappedCustomer);
    }

    @Test
    public void shouldMapCustomerToCustomerResponseAndReturn() {
        var address = Address.builder()
                .houseNumber("1")
                .street("street")
                .zipCode("123").
                build();
        var customer = Customer.builder()
                .id("1")
                .firstName("John")
                .lastName("Deer")
                .email("john.deer@email.com")
                .address(address)
                .build();
        var expectedCustomerResponse = new CustomerResponse("1", "John", "Deer", "john.deer@email.com", address);

        var mappedCustomerCustomerResponse = customerMapper.fromCustomer(customer);

        assertEquals(expectedCustomerResponse, mappedCustomerCustomerResponse);
    }

    @Test
    public void should_throw_null_pointer_exception_when_customer_is_null() {
        var exp = assertThrows(NullPointerException.class, () -> customerMapper.fromCustomer(null));
        assertEquals("Customer should not be null", exp.getMessage());
    }
}
