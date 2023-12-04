package com.genesis.commons.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommonExceptionHandler {

    Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail accessDeniedExceptionHandler(AccessDeniedException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.FORBIDDEN, e.getMessage())
                .type(URI.create("https://problems.genesis.com/access-denied"))
                .title("Access Denied")
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail authenticationExceptionHandler(AuthenticationException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage())
                .type(URI.create("https://problems.genesis.com/authentication-error"))
                .title("Authentication Error")
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage())
                .type(URI.create("https://problems.genesis.com/resource-not-found"))
                .title("Resource Not Found")
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.NOT_FOUND, e.getMessage())
                .type(URI.create("https://problems.genesis.com/endpoint-not-found"))
                .title("Endpoint Not Found")
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail bindExceptionHandler(BindException e) {
        Map<String, ViolationError> violations = new HashMap<>();

        for (var error : e.getBindingResult().getFieldErrors()) {
            if (violations.containsKey(error.getField())) {
                violations.get(error.getField()).messages().add(error.getDefaultMessage());
            } else {
                violations.put(
                        error.getField(),
                        new ViolationError(error.getField(),
                                List.of(Optional.ofNullable(error.getDefaultMessage())
                                        .orElse("This field is not valid"))
                        )
                );
            }
        }

        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.BAD_REQUEST, "Validation error on request object")
                .type(URI.create("https://problems.genesis.com/validation-error"))
                .title("Validation error")
                .violations(new ArrayList<>(violations.values()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    default ProblemDetail handleGlobalException(Exception e) {
        log.error("Stack trace of Internal Server Error", e);
        return ProblemDetailsBuilder.statusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .type(URI.create("https://problems.genesis.com/internal-server-error"))
                .title("Internal Server Error")
                .build();
    }

}
