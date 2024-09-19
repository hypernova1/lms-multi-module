package org.sam.gateway.security

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
class JwtAuthenticationFilter(
    private val customUserDetailsService: CustomUserDetailsService,
    private val tokenProvider: JwtTokenProvider
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val jwt = getJwtFromRequest(exchange)
        return if (jwt != null) {
            return validateToken(jwt)
                .flatMap { userDetails ->
                    exchange.mutate()
                        .request {
                            it.headers { headers ->
                                headers.add("X-User-ID", userDetails.username)
                                headers.add("X-User-Role", userDetails.authorities.first().toString())
                            }
                        }
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    chain.filter(exchange)
                }
                .onErrorResume {
                    it.printStackTrace()
                    throw UnauthorizedException(ErrorCode.UNAUTHORIZED_TOKEN)
                }
        } else {
            chain.filter(exchange)
        }
    }

    private fun getJwtFromRequest(exchange: ServerWebExchange): String? {
        val bearerToken = exchange.request.headers.getFirst("Authorization")
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    private fun validateToken(jwt: String): Mono<UserDetails> {
        return try {
            val id = tokenProvider.getPayload(jwt)!!
            customUserDetailsService.findByUsername(id)
        } catch (e: Exception) {
            Mono.error(e)
        }
    }
}