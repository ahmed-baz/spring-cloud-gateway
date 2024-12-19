package com.skyros.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/courses/filter",
            "/api/v1/account/**",
            "/actuator/**",
            "/error/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/index.html",
            "/webjars/**"
    };

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
                        .pathMatchers("/eureka/**").permitAll()
                        .anyExchange().authenticated());

        serverHttpSecurity
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> Customizer.withDefaults()));

        return serverHttpSecurity.build();
    }

}
