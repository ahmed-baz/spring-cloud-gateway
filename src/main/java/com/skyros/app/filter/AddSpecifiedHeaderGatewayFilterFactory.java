package com.skyros.app.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AddSpecifiedHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(final Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .headers((httpHeaders) -> httpHeaders.add("username", getPrincipleName())).build();
            return chain.filter(exchange.mutate().request(request).build());
        }, 16);
    }

    private String getPrincipleName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : authentication.getName();
    }


}
