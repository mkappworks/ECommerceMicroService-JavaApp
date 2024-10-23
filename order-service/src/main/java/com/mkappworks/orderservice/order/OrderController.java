package com.mkappworks.orderservice.order;

import com.mkappworks.orderservice.customer.CustomerService;
import com.mkappworks.orderservice.kafka.OrderProducer;
import com.mkappworks.orderservice.orderline.OrderLineMapper;
import com.mkappworks.orderservice.orderline.OrderLineService;
import com.mkappworks.orderservice.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        // check the customer -> customer-ms using OpenFeign
        var customer = customerService.findCustomerById(orderRequest.customerId());

        //purchase the products -> product-ms using RestTemplate
        var purchasedProducts = productService.getProducts(orderRequest.products());

        var order = orderService.createOrder(orderMapper.toOrder(orderRequest));

        var orderLineRequests = orderLineMapper.toOrderLineRequests(order, purchasedProducts);
        var orderLines = orderLineMapper.toOrderLines(orderLineRequests);
        orderLineService.saveOrderLines(orderLines);

        //start payment process

        //send the order confirmation -> notification-ms (kafka)
        var orderConfirmation = orderMapper.toOrderConfirmation(order, customer, purchasedProducts);
        orderProducer.sendOrderConfirmation(orderConfirmation);

        return ResponseEntity.ok(order.getId());
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        var orders = orderService.findAllOrders();
        return ResponseEntity.ok(orderMapper.fromOrders(orders));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Integer id) {
        var order = orderService.findOrderById(id);
        return ResponseEntity.ok(orderMapper.fromOrder(order));
    }
}
