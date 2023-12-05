package com.genesis.orderservice.dto.response;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        String id,
        Instant createdAt,
        Instant updatedAt,
        @Nullable
        String createdBy,
        @Nullable
        String updatedBy,
        BigDecimal total,
        List<CartItemResponse> items
) {

    public record CartItemResponse(
            String productId,
            Long quantity
    ) {}

}
