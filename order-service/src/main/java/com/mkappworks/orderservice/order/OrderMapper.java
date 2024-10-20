package com.mkappworks.orderservice.order;

import com.mkappworks.ecommerce.product.PurchaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest, List<PurchaseResponse> purchaseResponses) {
        return Order.builder()
                .id(orderRequest.id())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.amount())
                .paymentMethod(orderRequest.paymentMethod())
                .customerId(orderRequest.customerId())
                .build();
    }
}
