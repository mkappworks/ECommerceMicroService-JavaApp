package com.mkappworks.orderservice.orderline;

import lombok.Builder;

@Builder
public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
