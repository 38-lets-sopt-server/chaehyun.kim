package org.sopt.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		Server devServer = new Server().url("/").description("개발 서버");
		Server prodServer = new Server().url("/api").description("운영 서버");

		String securityJwtName = "JWT Auth";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);

		// 2. HTTP 헤더의 Authorization: Bearer <토큰> 스펙 정의
		Components components = new Components()
			.addSecuritySchemes(securityJwtName, new SecurityScheme()
				.name(securityJwtName)
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT"));

		return new OpenAPI()
			.servers(List.of(devServer, prodServer))
			.info(getInfo())
			.addSecurityItem(securityRequirement)
			.components(components);
	}

	private Info getInfo() {
		return new Info()
			.version("0.1.0")
			.title("SOPT 38th Assignment API")
			.description("SOPT 3차 세미나 과제 - 게시판 API 명세서");
	}

}
