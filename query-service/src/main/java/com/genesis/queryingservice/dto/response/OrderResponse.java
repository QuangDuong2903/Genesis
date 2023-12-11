package com.genesis.queryingservice.dto.response;

import com.genesis.commons.enumeration.OrderStatus;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.io.Serializable;
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
        UserResponse user,
        BigDecimal total,
        OrderStatus status,
        List<OrderProductResponse> orderProducts
) implements Serializable {

    public record UserResponse(
            String id,
            String username
    ) implements Serializable {}

    @Builder
    public record OrderProductResponse(
            String id,
            String name,
            BigDecimal price,
            Long quantity
    ) implements Serializable {}

}
