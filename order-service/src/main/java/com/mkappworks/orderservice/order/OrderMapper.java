package com.mkappworks.orderservice.order;

import com.mkappworks.orderservice.customer.CustomerResponse;
import com.mkappworks.orderservice.kafka.OrderConfirmation;
import com.mkappworks.orderservice.product.PurchaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.id())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.amount())
                .paymentMethod(orderRequest.paymentMethod())
                .customerId(orderRequest.customerId())
                .build();
    }

    public OrderConfirmation toOrderConfirmation(Order order, CustomerResponse customer, List<PurchaseResponse> products) {
        return OrderConfirmation.builder()
                .orderReference(order.getId().toString())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .customer(customer)
                .products(products)
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .reference(order.getReference())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .customerId(order.getCustomerId())
                .build();
    }

    public List<OrderResponse> fromOrders(List<Order> orders) {
        return orders
                .stream()
                .map(this::fromOrder)
                .collect(Collectors.toList());
    }
}
