package org.sam.lms.infra.swagger.annotation

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponse(responseCode = "200")
annotation class SwaggerOkResponse(
    val summary: String = "",
    val implementation: Array<KClass<*>> = [],
    val content: Array<Content> = [],
    val description: String = ""
)