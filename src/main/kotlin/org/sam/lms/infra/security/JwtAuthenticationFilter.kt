package org.sam.lms.infra.security

import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter


@Configuration
class JwtAuthenticationFilter(private val customUserDetailsService: CustomUserDetailsService, private val tokenProvider: JwtTokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt: String? = this.getJwtFromRequest(request)
        try {
            if (jwt != null) {
                val payload: Claims? = tokenProvider.getPayload(jwt)
                if (payload!!["type"] != "access") {
                    throw UnauthorizedException(ErrorCode.UNABLE_TO_REGISTER_USER)
                }
                val id = payload.get("userId", String::class.java)
                val userDetails = customUserDetailsService.loadUserByUsername(id)
                val authenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        } catch (e: Exception) {
            throw UnauthorizedException(ErrorCode.UNAUTHORIZED_TOKEN)
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7)
        }
        return null
    }

}