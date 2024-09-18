package org.sam.lms.api.swagger.annotation

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.sam.lms.common.exception.ErrorResult

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponse(
    responseCode = "403",
    content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResult::class))]
)
annotation class SwaggerForbiddenError(val description: String = "")