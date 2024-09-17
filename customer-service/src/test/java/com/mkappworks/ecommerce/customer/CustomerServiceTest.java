package com.mkappworks.ecommerce.customer;

import com.mkappworks.ecommerce.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private final Address address = Address.builder()
            .houseNumber("1")
            .street("street")
            .zipCode("123").
            build();
    private final Customer customerWithOutId = Customer.builder()
            .firstName("John")
            .lastName("Deer")
            .email("john.deer@email.com")
            .address(address)
            .build();

    private final Customer customerWithId = Customer.builder()
            .id("1")
            .firstName("John")
            .lastName("Deer")
            .email("john.deer@email.com")
            .address(address)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_customer() {
        when(customerRepository.save(customerWithOutId)).thenReturn(customerWithId);

        var customerId = customerService.createCustomer(customerWithOutId);

        assertEquals("1", customerId);
        verify(customerRepository, times(1)).save(customerWithOutId);
    }

    @Test
    public void should_successfully_update_customer() {
        when(customerRepository.findById("1")).thenReturn(Optional.ofNullable(customerWithId));

        assert customerWithId != null;
        customerService.updateCustomer(customerWithId);

        verify(customerRepository, times(1)).findById("1");
        verify(customerRepository, times(1)).save(customerWithId);
    }

    @Test
    public void should_fail_update_customer_if_customer_does_not_exist() {
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        var exp = assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customerWithId));
        assertEquals("Cannot update customer:: Customer with id '1' not found", exp.getMsg());
        verify(customerRepository, times(1)).findById("1");
        verify(customerRepository, times(0)).save(customerWithId);
    }

    @Test
    public void should_successfully_get_all_customers() {
        when(customerRepository.findAll()).thenReturn(List.of(customerWithId, customerWithId));

        var customers = customerService.getCustomers();

        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void should_successfully_check_if_customer_exists_by_id_and_return_true_if_present() {
        when(customerRepository.findById("1")).thenReturn(Optional.ofNullable(customerWithId));

        var doesExit = customerService.existById("1");

        assertEquals(true, doesExit);
        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    public void should_successfully_check_if_customer_exists_by_id_and_return_false_if_not_present() {
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        var doesExit = customerService.existById("1");

        assertEquals(false, doesExit);
        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    public void should_successfully_get_customer_by_id() {
        when(customerRepository.findById("1")).thenReturn(Optional.ofNullable(customerWithId));

        var customer = customerService.findCustomerById("1");

        assertEquals(customerWithId, customer);
        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    public void should_throw_get_customer_by_id_if_customer_does_not_exist() {
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        var exp = assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById("1"));
        assertEquals("Customer with id '1' not found", exp.getMsg());
        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    public void should_successfully_delete_customer_by_id() {
        customerService.deleteCustomer("1");

        verify(customerRepository, times(1)).deleteById("1");
    }

}