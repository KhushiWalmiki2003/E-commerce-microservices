
package com.example.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class JwtGatewayFilter implements GatewayFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> PUBLIC_URLS =
            List.of(
                    "/api/users/register",
                    "/api/users/login"
            );

    private static final Map<String, Map<String, List<String>>> ROLE_API_PERMISSION =
            Map.of(
                    "ADMIN", Map.of(
                            "GET", List.of("/api/products"),
                            "POST", List.of("/api/products"),
                            "PUT", List.of("/api/products"),
                            "DELETE", List.of("/api/products")
                    ),
                    "USER", Map.of(
                            "GET", List.of("/api/products")
                    )
            );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        String method = exchange.getRequest()
                .getMethod()
                .name();

        // 1. Allow public URLs
        if (PUBLIC_URLS.contains(path)) {
            return chain.filter(exchange);
        }

        // 2. Get Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // 3. Check Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        // 4. Validate JWT token
        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange);
        }

        // 5. Extract role from JWT
        String role = jwtUtil.getRole(token);

        // 6. Check role-based authorization
        if (!isAuthorized(role, path, method)) {
            return forbidden(exchange);
        }

        // 7. Extract user ID
        String userId = jwtUtil.getUserId(token);

        // 8. Forward user information to downstream services
        ServerHttpRequest modifiedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();

        return chain.filter(
                exchange.mutate()
                        .request(modifiedRequest)
                        .build()
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }

    // Unauthorized response (401)
    private Mono<Void> unauthorized(ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }

    // Forbidden response (403)
    private Mono<Void> forbidden(ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.FORBIDDEN);

        return exchange.getResponse().setComplete();
    }

    // Role-based authorization
    private boolean isAuthorized(String role, String path, String method) {

        Map<String, List<String>> permissions =
                ROLE_API_PERMISSION.get(role);

        if (permissions == null) {
            return false;
        }

        List<String> allowedPaths = permissions.get(method);

        if (allowedPaths == null) {
            return false;
        }

        return allowedPaths.stream()
                .anyMatch(path::startsWith);
    }
}
