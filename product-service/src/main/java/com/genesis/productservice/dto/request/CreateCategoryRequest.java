package com.genesis.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String slug
) {}
