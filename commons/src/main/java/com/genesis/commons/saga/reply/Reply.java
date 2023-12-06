package com.genesis.commons.saga.reply;

public record Reply<ID, T> (
        ID identifier,
        T payload,
        boolean success
) {

    public static <ID, T> Reply<ID, T> success(ID id, T data) {
        return new Reply<>(id, data, true);
    }

    public static <ID, T> Reply<ID, T> failure(ID id, T data) {
        return new Reply<>(id, data, false);
    }

}
