package com.genesis.commons.messaging;

public record Command<ID, T>(
        ID identifier,
        T payload
) {}
