package com.mkappworks.ecommerce.orderline;

import com.mkappworks.ecommerce.order.Order;
import com.mkappworks.ecommerce.product.PurchaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderLineMapper {
    private OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .productId(orderLineRequest.productId())
                .order(Order.builder()
                        .id(orderLineRequest.orderId())
                        .build())
                .quantity(orderLineRequest.quantity())
                .build();
    }

    public List<OrderLine> toOrderLines(List<OrderLineRequest> orderLineRequests) {
        return orderLineRequests
                .stream()
                .map(this::toOrderLine)
                .collect(Collectors.toList());
    }

    private OrderLineRequest toOrderLineRequest(PurchaseResponse product, Order order) {
        return OrderLineRequest.builder()
                .id(null)
                .productId(product.productId())
                .orderId(order.getId())
                .quantity(product.quantity())
                .build();
    }

    public List<OrderLineRequest> toOrderLineRequests(List<PurchaseResponse> products, Order order) {
        return products
                .stream()
                .map((product) -> toOrderLineRequest(product, order))
                .collect(Collectors.toList());
    }
}
