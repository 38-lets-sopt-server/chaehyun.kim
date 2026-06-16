package org.sopt.common.auth

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
            ?: throw IllegalArgumentException("회원이 존재하지 않습니다.")

        require(passwordEncoder.matches(password, user.password)) { "이메일 또는 비밀번호가 올바르지 않습니다." }

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
            .orElseThrow { IllegalArgumentException("회원이 존재하지 않습니다.") }
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
