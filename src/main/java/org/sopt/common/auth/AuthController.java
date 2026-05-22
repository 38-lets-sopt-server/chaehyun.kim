package org.sopt.common.auth;

import org.sopt.common.response.CustomAPIResponse;
import org.sopt.common.response.SuccessStatus;
import org.sopt.user.dto.UserLoginRequest;
import org.sopt.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
	@PostMapping("/login")
	public ResponseEntity<CustomAPIResponse<TokenResponse>> login(
		@RequestBody UserLoginRequest request
	) {
		TokenResponse tokens = authService.login(request.email(), request.password());

		return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.LOGIN_SUCCESS, tokens));
	}

	@Operation(summary = "내 정보 조회 (Access Token 검증)")
	@GetMapping("/me")
	public ResponseEntity<CustomAPIResponse<UserResponse>> me(Authentication authentication) {

		if (authentication == null || authentication.getPrincipal() == null) {
			throw new IllegalArgumentException("인증되지 않았습니다.");
		}

		Long userId = Long.parseLong(authentication.getName());
		UserResponse user = authService.getMemberById(userId);

		return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.GET_USER_PROFILE_SUCCESS, user));
	}
}
