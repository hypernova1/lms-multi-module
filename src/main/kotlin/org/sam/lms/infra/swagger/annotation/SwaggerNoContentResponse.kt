package org.sam.lms.infra.swagger.annotation

import io.swagger.v3.oas.annotations.responses.ApiResponse

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponse(responseCode = "204")
annotation class SwaggerNoContentResponse(
    val summary: String = "",
    val description: String = ""
)