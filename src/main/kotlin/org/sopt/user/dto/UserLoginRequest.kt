package org.sopt.user.dto

import io.swagger.v3.oas.annotations.media.Schema


data class UserLoginRequest(
    @field:Schema(
        description = "유저 로그인 이메일",
        example = "wooseok@sopt.org"
    ) val email: String,

    @field:Schema(
        description = "유저 로그인 비밀번호",
        example = "password123!"
    ) val password: String
)
