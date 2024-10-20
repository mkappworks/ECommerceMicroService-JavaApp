package com.mkappworks.ecommerce.order;

import com.mkappworks.ecommerce.customer.CustomerService;
import com.mkappworks.ecommerce.orderline.OrderLineMapper;
import com.mkappworks.ecommerce.orderline.OrderLineService;
import com.mkappworks.ecommerce.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderLineService orderLineService;
    private final OrderLineMapper orderLineMapper;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        var customer = customerService.findCustomerById(orderRequest.customerId());

        var products = productService.getProducts(orderRequest.products());

        var order = orderService.createOrder(orderMapper.toOrder(orderRequest, products));

        var orderLineRequests = orderLineMapper.toOrderLineRequests(products, order);
        var orderLines = orderLineMapper.toOrderLines(orderLineRequests);
        orderLineService.saveOrderLines(orderLines);

        //start payment process

        //send the order confirmation -> notification-ms (kafka)

        return ResponseEntity.ok(1);
    }
}
