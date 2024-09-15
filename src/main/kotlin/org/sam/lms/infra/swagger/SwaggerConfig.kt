package org.sam.lms.infra.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    info = Info(
        title = "API Documentation", description = "", version = "v1"
    )
)
@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val securityJWTName = "JWT"
        val securityRequirement: SecurityRequirement = SecurityRequirement().addList(securityJWTName)
        val components: Components = Components()
            .addSecuritySchemes(
                securityJWTName, SecurityScheme()
                    .name(securityJWTName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme(BEARER_TOKEN_PREFIX)
                    .bearerFormat(securityJWTName)
            )

        return OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components)
    }

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer"
    }
}