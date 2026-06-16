package org.sopt.user.mapper

import org.sopt.user.domain.User
import org.sopt.user.dto.UserResponse

fun User.toResponse() = UserResponse(
    id = id,
    name = name,
    email = email
)
