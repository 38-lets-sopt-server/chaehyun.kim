package org.sopt.common.auth;

import java.time.Duration;

import org.sopt.common.response.CustomAPIResponse;
import org.sopt.common.response.SuccessStatus;
import org.sopt.user.dto.UserLoginRequest;
import org.sopt.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@Value("${security.cookie.secure:true}")
	private boolean cookieSecure;

	@Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
	@PostMapping("/login")
	public ResponseEntity<CustomAPIResponse<TokenResponse>> login(
		@RequestBody UserLoginRequest request,
		HttpServletResponse response
	) {
		AuthTokens authTokens = authService.login(request.email(), request.password());

		ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", authTokens.refreshToken())
			.httpOnly(true)
			.secure(cookieSecure)
			.path("/api/v1/auth")
			.maxAge(Duration.ofDays(14))
			.sameSite("Strict")
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

		return ResponseEntity.ok(
			CustomAPIResponse.createSuccess(SuccessStatus.LOGIN_SUCCESS, TokenResponse.of(authTokens.accessToken()))
		);
	}

	@Operation(summary = "내 정보 조회 (Access Token 검증)")
	@GetMapping("/me")
	public ResponseEntity<CustomAPIResponse<UserResponse>> me(@UserId Long userId) {
		UserResponse user = authService.getMemberById(userId);

		return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.GET_USER_PROFILE_SUCCESS, user));
	}

	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<CustomAPIResponse<Void>> logout(
		@UserId Long userId,
		HttpServletRequest request,
		HttpServletResponse response
	){
		String accessToken = (String) request.getAttribute("accessToken");
		authService.logout(userId, accessToken);

		ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", "")
			.httpOnly(true)
			.secure(cookieSecure)
			.path("/api/v1/auth")
			.maxAge(0)
			.sameSite("Strict")
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());

		return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.LOGOUT_SUCCESS, null));
	}
}
