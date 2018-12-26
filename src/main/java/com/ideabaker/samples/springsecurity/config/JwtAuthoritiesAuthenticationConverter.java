package com.ideabaker.samples.springsecurity.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-25 15:50
 */
public class JwtAuthoritiesAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtAuthenticationConverter delegate;

    JwtAuthoritiesAuthenticationConverter(JwtAuthenticationConverter delegate) {
        this.delegate = delegate;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Stream<GrantedAuthority> scopeAuthorities = delegate.convert(jwt).getAuthorities().stream();

        Stream<SimpleGrantedAuthority> authorities = attrs(jwt)
                .stream()
                .map(SimpleGrantedAuthority::new);
        return Stream.concat(scopeAuthorities, authorities)
                .collect(Collectors.toList());
    }

    private Collection<String> attrs(Jwt jwt) {
        Object scopes = jwt.getClaims().get("authorities");
        if (scopes instanceof String) {
            if (StringUtils.hasText((String) scopes)) {
                return Arrays.asList(((String) scopes).split(" "));
            } else {
                return Collections.emptyList();
            }
        } else if (scopes instanceof Collection) {
            return (Collection<String>) scopes;
        }

        return Collections.emptyList();
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = this.extractAuthorities(jwt);
        JwtAuthenticationToken auth = new JwtAuthenticationToken(jwt, authorities);
        return Mono.just(auth);
    }
}
