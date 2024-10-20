package com.mkappworks.ecommerce.customer;

import com.mkappworks.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerClient customerClient;

    public CustomerResponse findCustomerById(String customerId) {
        // check the customer -> customer-ms using OpenFeign
        return customerClient.findCustomerById(customerId)
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exits with id: " + customerId));
    }
}
