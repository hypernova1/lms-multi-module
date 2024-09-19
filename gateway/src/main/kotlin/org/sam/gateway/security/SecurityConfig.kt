package org.sam.gateway.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationEntryPoint: ServerAuthenticationEntryPoint,
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeExchange { exchange ->
                exchange
                    .pathMatchers("/lms/api/**").permitAll()
                    .pathMatchers("/lms/api/**").permitAll()
                    .pathMatchers("/h2-console/**", "/swagger*/**", "/api-docs/**").permitAll()
                    .anyExchange().authenticated()
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
            }
            .build()
    }

}