package com.mkappworks.ecommerce.orderline;

import lombok.Builder;

@Builder
public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
