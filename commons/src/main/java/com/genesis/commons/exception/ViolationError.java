package com.genesis.commons.exception;

import java.util.List;

public record ViolationError(
        String field,
        List<String> messages
) {}