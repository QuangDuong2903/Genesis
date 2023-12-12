package com.genesis.authservice.dto.response;

import com.genesis.commons.enumeration.OrderStatus;
import lombok.Builder;
import lombok.With;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@With
public record UserResponse(
        String id,
        Instant createdAt,
        Instant updatedAt,
        @Nullable
        String createdBy,
        @Nullable
        String updatedBy,
        String username,
        BigDecimal balance,
        List<RoleResponse> roles,
        List<OrderResponse> orders
) {

    @Builder
    public record RoleResponse(
            String id,
            String name,
            String code
    ) {}

    @Builder
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

        @Builder
        public record OrderItemResponse(
                String id,
                Long quantity
        ) {}

    }

}
