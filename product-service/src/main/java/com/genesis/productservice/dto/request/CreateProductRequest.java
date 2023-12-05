package com.genesis.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String image,
        @NotNull
        Long quantity,
        @NotNull
        BigDecimal price,
        @NotBlank
        String categoryId
) {}
