package org.sopt.auth

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

    fun rotate(
        newToken: String,
        expiresInSeconds: Long
    ) {
        token = newToken
        expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds)
    }
}
