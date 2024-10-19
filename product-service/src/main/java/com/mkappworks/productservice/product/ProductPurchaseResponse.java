package com.mkappworks.productservice.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductPurchaseResponse(
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price
) {
}
