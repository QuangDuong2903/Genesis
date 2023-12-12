package com.genesis.authservice.client.dto.response;

import com.genesis.commons.enumeration.OrderStatus;
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
        OrderStatus status,
        List<OrderItemResponse> items
) {

    public record OrderItemResponse(
            String productId,
            Long quantity
    ) {}

}
