package com.genesis.commons.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public record MultiStatusResponse(
        long totalItems,
        long totalSuccessItems,
        long totalFailureItems,
        List<Item> items
) {

    public static MultiStatusResponse of(List<Item> items) {
        long totalItems = items.size();
        long totalSuccessItems = items.stream().filter(item -> item instanceof Success<?>).count();
        long totalFailureItems = totalItems - totalSuccessItems;
        return new MultiStatusResponse(totalItems, totalSuccessItems, totalFailureItems, items);
    }

    public sealed interface Item permits Success, Failure {}

    public record Success<T>(int status, T data) implements Item {

        public static <T> Success<T> ok(T data) {
            return new Success<>(HttpStatus.OK.value(), data);
        }

        public static <T> Success<T> created(T data) {
            return new Success<>(HttpStatus.CREATED.value(), data);
        }

    }

    public record Failure(int status, String message) implements Item {

        public static Failure internalServerError(String message) {
            return new Failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
        }

    }

}
