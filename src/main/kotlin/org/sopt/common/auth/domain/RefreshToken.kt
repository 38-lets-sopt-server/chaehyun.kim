package org.sopt.common.auth.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class RefreshToken private constructor(

    @Column(nullable = false)
    var userId: Long,

    @Column(
        nullable = false,
        unique = true
    )
    var token: String,

    @Column(nullable = false)
    var expiresAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    constructor(
        userId: Long,
        token: String,
        expiresInSeconds: Long
    ) : this(
        userId = userId,
        token = token,
        expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds)
    )

    fun rotate(
        newToken: String,
        expiresInSeconds: Long
    ) {
        token = newToken
        expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds)
    }
}
