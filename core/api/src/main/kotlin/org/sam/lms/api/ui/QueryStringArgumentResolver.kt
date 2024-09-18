package org.sam.lms.api.ui

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Configuration
class QueryStringArgumentResolver(private val mapper: ObjectMapper) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(QueryStringArgument::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest
        if (request.queryString == null) {
            return parameter.getParameterType().newInstance()
        }
        val json: String = qs2json(request.queryString)
        return mapper.readValue(json, parameter.getParameterType())
    }

    private fun qs2json(a: String): String {
        var res = "{\""
        for (i in a.indices) {
            if (a[i] == '=') {
                res += "\"" + ":" + "\""
            } else if (a[i] == '&') {
                res += "\"" + "," + "\""
            } else {
                res += a[i]
            }
        }
        res += "\"" + "}"
        return res
    }
}