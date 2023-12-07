package com.genesis.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String slug
) {}
