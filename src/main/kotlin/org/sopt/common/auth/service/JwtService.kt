package org.sopt.common.auth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JwtService(
    @Value("\${security.jwt.secret}") secret: String,
    @param:Value("\${security.jwt.access-token-expires-in-seconds:1800}") private val accessTokenExpiresInSeconds: Long,
    @param:Value("\${security.jwt.refresh-token-expires-in-seconds:1209600}") private val refreshTokenExpiresInSeconds: Long
) {
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier = JWT.require(algorithm).build()

    fun generateAccessToken(memberId: Long, email: String): String {
        val now = Instant.now()
        return JWT.create()
            .withSubject(memberId.toString())
            .withClaim("email", email)
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plusSeconds(accessTokenExpiresInSeconds)))
            .sign(algorithm)
    }

    fun generateRefreshToken(memberId: Long): String {
        val now = Instant.now()
        return JWT.create()
            .withSubject(memberId.toString())
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plusSeconds(refreshTokenExpiresInSeconds)))
            .sign(algorithm)
    }

    fun verifyAndGetUserId(token: String): Long {
        if (token.isBlank()) {
            throw BusinessException(ErrorCode.INVALID_AUTHENTICATION)
        }

        return verifier.verify(token)
            .subject
            .toLongOrNull()
            ?: throw BusinessException(ErrorCode.INVALID_AUTHENTICATION)
    }

    fun getTokenRemainingSeconds(token: String): Long {
        val jwt = verifier.verify(token)
        val expEpoch = jwt.expiresAt.toInstant().epochSecond
        return (expEpoch - Instant.now().epochSecond)
            .coerceAtLeast(0)
    }
}
