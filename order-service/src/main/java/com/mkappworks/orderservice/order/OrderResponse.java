package com.mkappworks.orderservice.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
