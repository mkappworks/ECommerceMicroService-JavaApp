package com.mkappworks.orderservice.order;

import com.mkappworks.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be required")
        @NotEmpty(message = "Customer should be required")
        @NotBlank(message = "Customer should be required")
        String customerId,
        @NotEmpty(message = "Should at least purchase one product")
        List<PurchaseRequest> products
        ) {
}
