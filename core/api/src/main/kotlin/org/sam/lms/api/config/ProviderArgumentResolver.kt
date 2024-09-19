package org.sam.lms.api.config

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.sam.lms.domain.account.domain.Provider
import org.sam.lms.domain.account.domain.RoleName
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Configuration
class ProviderArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RequireAuth::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        var provider: Provider? = null

        val userId: String? = webRequest.getHeader("X-User-ID")
        val userRole: String? = webRequest.getHeader("X-User-Role")
        if (userId != null && userRole != null) {
            provider = Provider(userId.toLong(), role = RoleName.from(userRole.removePrefix("ROLE_")))
        }

        if (provider == null) {
            throw UnauthorizedException(ErrorCode.UNAUTHORIZED_TOKEN)
        }

        return provider
    }
}