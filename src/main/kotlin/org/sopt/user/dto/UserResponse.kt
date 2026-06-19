package org.sopt.user.dto

import org.sopt.user.domain.User

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
){
    constructor(user: User) : this(
        id = requireNotNull(user.id),
        name = user.name,
        email = user.email
    )
}
