package com.mkappworks.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        var customer = customerMapper.toCustomer(request);
        return new ResponseEntity<String>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) {
        var customer = customerMapper.toCustomer(request);
        customerService.updateCustomer(customer);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        var customerResponses = customerService
                .getCustomers()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());

        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.existById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable String id) {
        var customerResponse = customerMapper.fromCustomer(customerService.findCustomerById(id));
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }

}
