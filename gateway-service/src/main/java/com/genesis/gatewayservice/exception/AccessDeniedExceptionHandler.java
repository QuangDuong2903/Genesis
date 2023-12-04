package com.genesis.gatewayservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("Content-Type", "application/json");

        DataBuffer buffer;
        try {
            buffer = response.bufferFactory().wrap(
                    objectMapper.writeValueAsString(
                                    ProblemDetailsBuilder.statusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage())
                                            .title("Forbidden")
                                            .type(URI.create("about:blank"))
                                            .instance(URI.create(exchange.getRequest().getURI().getPath()))
                                            .build())
                            .getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return response.writeWith(Mono.just(buffer));
    }
}
