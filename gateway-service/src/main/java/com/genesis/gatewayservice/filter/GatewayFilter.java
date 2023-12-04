package com.genesis.gatewayservice.filter;

import com.genesis.gatewayservice.utils.CustomHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GatewayFilter implements GlobalFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || token.startsWith("Basic"))
            return chain.filter(exchange);
        Jwt jwt = jwtDecoder.decode(token.substring(7));
        return chain.filter(
                exchange.mutate().request(
                                exchange.getRequest().mutate()
                                        .header(CustomHeaders.X_AUTH_USER_ID, jwt.getClaimAsString("id"))
                                        .header(CustomHeaders.X_AUTH_USER_AUTHORITIES,
                                                String.valueOf(jwt.getClaimAsString("authorities")))
                                        .build()
                        )
                        .build()
        );
    }
}
