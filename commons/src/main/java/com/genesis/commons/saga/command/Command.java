package com.genesis.commons.saga.command;

public record Command<ID, T>(
        ID identifier,
        T payload
) {}
