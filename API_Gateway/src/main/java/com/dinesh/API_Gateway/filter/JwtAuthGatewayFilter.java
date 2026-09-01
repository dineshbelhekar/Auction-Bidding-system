package com.dinesh.API_Gateway.filter;

import com.dinesh.API_Gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthGatewayFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    // Paths that should skip JWT validation (login, register, public health checks)
    private static final List<String> OPEN_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/signup",
            "/actuator/health",
            "/api/auth/getotp",
            "/api/auth/verify"

    );

    public JwtAuthGatewayFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isOpenPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);


        try {

            Claims claims = jwtUtil.validateAndExtractClaims(token);

            String userId = claims.getSubject();
            String username = claims.get("username", String.class);


            // Strip any client-supplied identity headers, then set verified ones
            ServerHttpRequest mutatedRequest = request.mutate()
                    .headers(headers -> {
                        headers.remove("X-User-Id");
                        headers.remove("X-Username");

                        headers.add("X-User-Id", userId);
                        if (username != null) headers.add("X-Username", username);

                    })
                    .build();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange);

        } catch (JwtException e) {
            return unauthorized(exchange, "Invalid or expired token: " + e.getMessage());
        }
    }

    private boolean isOpenPath(String path) {
        return OPEN_PATHS.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String body = String.format("{\"error\": \"%s\"}", message);
        var buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1; // run before routing
    }
}