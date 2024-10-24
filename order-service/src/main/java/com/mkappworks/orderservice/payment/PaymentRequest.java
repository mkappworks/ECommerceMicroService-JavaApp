package com.mkappworks.orderservice.payment;

import com.mkappworks.orderservice.customer.CustomerResponse;
import com.mkappworks.orderservice.order.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
