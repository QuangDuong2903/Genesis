package com.genesis.commons.cqrs.aggregate;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductAggregate(
        Long id,
        short version,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy,
        String name,
        String description,
        String image,
        Long quantity,
        BigDecimal price,
        Long categoryId
) {}
