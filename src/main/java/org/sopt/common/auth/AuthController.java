package org.sopt.common.auth;

import org.sopt.common.response.CustomAPIResponse;
import org.sopt.common.response.SuccessStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
		@RequestParam("email") String email,
		@RequestParam("password") String password
	) {
		TokenResponse tokens = authService.login(email, password);

		return ResponseEntity.ok(CustomAPIResponse.createSuccess(SuccessStatus.LOGIN_SUCCESS, tokens));
	}
}
