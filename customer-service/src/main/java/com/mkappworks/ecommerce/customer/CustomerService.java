package com.mkappworks.ecommerce.customer;

import com.mkappworks.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public String createCustomer(Customer customer) {
        var savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }

    public void updateCustomer(Customer requestCustomer) {
        var dbCustomer = customerRepository.findById(requestCustomer.getId())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Cannot update customer:: Customer with id '%s' not found", requestCustomer.getId())));

        mergerCustomer(dbCustomer, requestCustomer);
        customerRepository.save(dbCustomer);
    }

    private void mergerCustomer(Customer dbCustomer, Customer requestCustomer) {
        if (StringUtils.isNotBlank(requestCustomer.getFirstName())) {
            dbCustomer.setFirstName(requestCustomer.getFirstName());
        }
        if (StringUtils.isNotBlank(requestCustomer.getLastName())) {
            dbCustomer.setLastName(requestCustomer.getLastName());
        }
        if (StringUtils.isNotBlank(requestCustomer.getEmail())) {
            dbCustomer.setEmail(requestCustomer.getEmail());
        }
        if (requestCustomer.getAddress() != null) {
            dbCustomer.setAddress(requestCustomer.getAddress());
        }
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Boolean existById(String id) {
        return customerRepository.findById(id)
                .isPresent();
    }

    public Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id '%s' not found", id)));
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
