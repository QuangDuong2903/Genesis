package com.genesis.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        @NotNull
        BigDecimal total,
        @NotNull
        List<OrderItemRequest> items
) {

    public record OrderItemRequest(
            @NotBlank
            String productId,
            @NotNull
            Long quantity
    ) {}

}
