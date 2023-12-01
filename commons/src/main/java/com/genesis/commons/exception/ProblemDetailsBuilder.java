package com.genesis.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.Instant;
import java.util.List;

public class ProblemDetailsBuilder {

    private final ProblemDetail problemDetail;

    public static ProblemDetailsBuilder statusAndDetail(HttpStatus status, String detail) {
        return new ProblemDetailsBuilder(status, detail);
    }

    private ProblemDetailsBuilder(HttpStatus status, String detail) {
        this.problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        this.problemDetail.setProperty("timestamp", Instant.now());
    }

    public ProblemDetail build() {
        return problemDetail;
    }

    public ProblemDetailsBuilder type(URI type) {
        this.problemDetail.setType(type);
        return this;
    }

    public ProblemDetailsBuilder title(String title) {
        this.problemDetail.setTitle(title);
        return this;
    }

    public ProblemDetailsBuilder violations(List<ViolationError> violations) {
        this.problemDetail.setProperty("violations", violations);
        return this;
    }
}
