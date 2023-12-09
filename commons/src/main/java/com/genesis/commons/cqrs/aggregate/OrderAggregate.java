package com.genesis.commons.cqrs.aggregate;

import com.genesis.commons.enumeration.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderAggregate(
        Long id,
        short version,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy,
        Long userId,
        BigDecimal total,
        OrderStatus status,
        List<CartItemResponse> items
) {

    public record CartItemResponse(
            Long productId,
            Long quantity
    ) {}

}
