package com.choulatte.scentgateway.filter;

import com.choulatte.scentgateway.auth.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (request.getMethod() == HttpMethod.POST
                    && (request.getPath().value().equals("/users") || request.getPath().value().equals("/users/join"))) {
                return chain.filter(exchange);
            }

            if (request.getHeaders().containsKey("Authorization")
                    && jwtTokenProvider.validateToken(Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0))) {
                return chain.filter(exchange);
            }

            ServerHttpResponse response = exchange.getResponse();

            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            return response.setComplete();
        }));
    }

    public static class Config {

    }
}