package org.sopt.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.sopt.common.auth.resolver.UserId
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = Info(
    title = "동치미 API",
    description = "동치미 API 명세서",
    version = "v1"
))
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .servers(
                listOf(
                    Server().url("/").description("개발 서버"),
                    Server().url("/api").description("운영 서버")))
            .addSecurityItem(SecurityRequirement().addList(JWT_AUTH))
            .components(
                Components()
                    .addSecuritySchemes(
                        JWT_AUTH, SecurityScheme()
                            .name(JWT_AUTH)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    ))

    companion object {
        private const val JWT_AUTH = "JWT Auth"

        init {
            SpringDocUtils.getConfig()
                .addAnnotationsToIgnore(UserId::class.java)
        }
    }

}
