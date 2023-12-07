package com.genesis.commons.cqrs.aggregate;

import java.time.Instant;

public record CategoryAggregate(
        Long id,
        short version,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy,
        String name,
        String slug
) {}
