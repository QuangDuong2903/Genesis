package com.genesis.authservice.exception;

import com.genesis.commons.exception.CommonExceptionHandler;
import com.genesis.commons.exception.ProblemDetailsBuilder;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ControllerExceptionHandler implements CommonExceptionHandler {

    @ExceptionHandler(CallNotPermittedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProblemDetail callNotPermittedExceptionHandler(CallNotPermittedException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage())
                .type(URI.create("https://problems.genesis.com/service-unavailable"))
                .title("Service Unavailable")
                .build();
    }
}
