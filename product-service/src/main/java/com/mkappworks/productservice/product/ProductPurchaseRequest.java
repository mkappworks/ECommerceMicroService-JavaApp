package com.mkappworks.productservice.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product is required")
        Integer productId,
        @Positive(message = "Product quantity is required")
        double quantity
) {
}
