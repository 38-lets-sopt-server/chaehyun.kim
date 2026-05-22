package org.sopt.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginRequest(

	@Schema(description = "유저 로그인 이메일", example = "wooseok@sopt.org")
	String email,

	@Schema(description = "유저 로그인 비밀번호", example = "password123!")
	String password
) {
}
