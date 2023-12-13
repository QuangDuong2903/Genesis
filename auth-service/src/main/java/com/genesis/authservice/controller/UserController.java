package com.genesis.authservice.controller;

import com.genesis.authservice.dto.response.UserResponse;
import com.genesis.authservice.service.UserService;
import com.genesis.commons.exception.ProblemDetailsBuilder;
import com.genesis.commons.response.RestResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "order-service-client", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "order-service-client")
    @Retry(name = "order-service-client")
    @Bulkhead(name = "order-service-client", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<RestResponse<UserResponse>>> getOneUser(
            @RequestParam(required = false) boolean failure,
            @RequestParam(required = false) int delay,
            @PathVariable Long id
    ) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(userService.getOneUser(id, failure, delay)));
    }

    public CompletableFuture<ProblemDetail> fallbackMethod(Exception e) {
        return CompletableFuture.supplyAsync(() ->  ProblemDetailsBuilder.statusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage())
                .type(URI.create("https://problems.genesis.com/internal-server-error"))
                .title("Internal Server Error")
                .build()
        );
    }

}
