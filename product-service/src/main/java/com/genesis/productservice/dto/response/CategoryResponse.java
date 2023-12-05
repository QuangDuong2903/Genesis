package com.genesis.productservice.dto.response;

import org.springframework.lang.Nullable;

import java.time.Instant;

public record CategoryResponse(
        String id,
        Instant createdAt,
        Instant updatedAt,
        @Nullable
        String createdBy,
        @Nullable
        String updatedBy,
        String name,
        String slug
) {}
