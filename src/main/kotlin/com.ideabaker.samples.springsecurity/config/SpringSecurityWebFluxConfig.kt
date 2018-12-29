package com.ideabaker.samples.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.server.SecurityWebFilterChain

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 10:43
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SpringSecurityWebFluxConfig(private val reader: PublicKeyReader) {

    @Bean
    @Throws(Exception::class)
    internal fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/movie/**").hasRole("USER")
                //      .pathMatchers(HttpMethod.POST, "/api/movie/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(JwtAuthoritiesAuthenticationConverter(JwtAuthenticationConverter()))
                .publicKey(reader.publicKey())

        return http.build()

    }
}
