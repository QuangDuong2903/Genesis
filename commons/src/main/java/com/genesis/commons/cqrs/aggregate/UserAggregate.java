package com.genesis.commons.cqrs.aggregate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record UserAggregate(
        Long id,
        short version,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy,
        String username,
        String password,
        BigDecimal balance,
        List<Long> roles
) {}
