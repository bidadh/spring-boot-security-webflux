package com.ideabaker.samples.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SpringSecurityWebFluxConfig {

    private final PublicKeyReader reader;

    public SpringSecurityWebFluxConfig(PublicKeyReader reader) {
        this.reader = reader;
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/movie/**").hasRole("USER")
//      .pathMatchers(HttpMethod.POST, "/api/movie/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new JwtAuthoritiesAuthenticationConverter(new JwtAuthenticationConverter()))
                .publicKey(reader.publicKey());

        return http.build();

    }
}

