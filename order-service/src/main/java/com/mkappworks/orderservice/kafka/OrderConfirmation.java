package com.mkappworks.orderservice.kafka;

import com.mkappworks.orderservice.customer.CustomerResponse;
import com.mkappworks.orderservice.order.PaymentMethod;
import com.mkappworks.orderservice.product.PurchaseResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
