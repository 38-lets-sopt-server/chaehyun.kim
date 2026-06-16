package org.sopt.user.dto

data class UserCreateRequest(
    val name: String,
    val email: String,
    val password: String
)
