package org.sam.lms.client

import feign.RequestInterceptor
import feign.RequestTemplate
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class CustomFeignClientInterceptor(private val httpServletRequest: HttpServletRequest) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val userId = httpServletRequest.getHeader("X-User-ID")
        val userRole = httpServletRequest.getHeader("X-User-Role")

        if (userId != null) {
            template.header("X-User-ID", userId)
            template.header("X-User-Role", userRole)
        }
    }
}