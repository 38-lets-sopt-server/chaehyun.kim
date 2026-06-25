package org.sopt.common.auth.dto

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)
