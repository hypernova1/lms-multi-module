package org.sam.lms.infra.swagger.annotation

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.sam.lms.common.exception.ErrorResult

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponse(
    responseCode = "401",
    content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResult::class))]
)
annotation class SwaggerUnauthorizedError(val description: String = "")