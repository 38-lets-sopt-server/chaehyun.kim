package org.sopt.common.auth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)
