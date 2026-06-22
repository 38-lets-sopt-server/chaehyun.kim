package org.sopt.auth.dto

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)
