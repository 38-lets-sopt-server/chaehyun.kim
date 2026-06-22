package org.sopt.auth

import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.sopt.user.dto.UserResponse
import org.sopt.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val tokenBlacklistService: TokenBlacklistService
) {

    @Value("\${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private val refreshTokenExpiresInSeconds: Long = 0

    fun loginWithCredentials(email: String, password: String): UserResponse {
        val user = userRepository.findByEmail(email)
            ?: throw BusinessException(ErrorCode.INVALID_CREDENTIALS)

        if (!passwordEncoder.matches(password, user.password)) {
            throw BusinessException(ErrorCode.INVALID_CREDENTIALS)
        }

        return UserResponse(user)
    }

    @Transactional
    fun login(email: String, password: String): AuthTokens {
        val user = loginWithCredentials(email, password)

        val accessToken = jwtService.generateAccessToken(user.id, user.email)
        val refreshToken = jwtService.generateRefreshToken(user.id)

        refreshTokenRepository.deleteByUserId(user.id)
        refreshTokenRepository.save(
            RefreshToken.of(user.id, refreshToken, refreshTokenExpiresInSeconds)
        )

        return AuthTokens(accessToken, refreshToken)
    }

    fun getMemberById(memberId: Long): UserResponse {
        val user = userRepository.findById(memberId)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        return UserResponse(user)
    }

    @Transactional
    fun logout(userId: Long, accessToken: String) {
        refreshTokenRepository.deleteByUserId(userId)

        val remainingSeconds = jwtService.getTokenRemainingSeconds(accessToken)

        if (remainingSeconds > 0) {
            tokenBlacklistService.blacklist(accessToken, remainingSeconds)
        }
    }
}
