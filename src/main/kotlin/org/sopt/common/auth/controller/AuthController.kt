package org.sopt.common.auth.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.sopt.common.auth.service.AuthService
import org.sopt.common.auth.dto.TokenResponse
import org.sopt.common.auth.resolver.UserId
import org.sopt.common.response.CustomAPIResponse
import org.sopt.common.response.SuccessStatus
import org.sopt.user.dto.UserLoginRequest
import org.sopt.user.dto.UserResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @Value("\${security.cookie.secure:true}")
    private val cookieSecure = false

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    fun login(
        @RequestBody request: UserLoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<CustomAPIResponse<TokenResponse>> {
        val authTokens = authService.login(request.email, request.password)

        val refreshTokenCookie = ResponseCookie.from("refreshToken", authTokens.refreshToken)
            .httpOnly(true)
            .secure(cookieSecure)
            .path("/api/v1/auth")
            .maxAge(Duration.ofDays(14))
            .sameSite("Strict")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())

        return ResponseEntity.ok(
            CustomAPIResponse.createSuccess(SuccessStatus.LOGIN_SUCCESS, TokenResponse(authTokens.accessToken))
        )
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    fun me(@UserId userId: Long): ResponseEntity<CustomAPIResponse<UserResponse>> {
        val user = authService.getMemberById(userId)

        return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.GET_USER_PROFILE_SUCCESS, user))
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    fun logout(
        @UserId userId: Long,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<CustomAPIResponse<Void?>> {
        val accessToken = request.getAttribute("accessToken") as? String
            ?: throw IllegalArgumentException("Access Token이 존재하지 않습니다.")

        authService.logout(userId, accessToken)

        val expiredCookie = ResponseCookie.from("refreshToken", "")
            .httpOnly(true)
            .secure(cookieSecure)
            .path("/api/v1/auth")
            .maxAge(0)
            .sameSite("Strict")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString())

        return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.LOGOUT_SUCCESS, null))
    }
}
