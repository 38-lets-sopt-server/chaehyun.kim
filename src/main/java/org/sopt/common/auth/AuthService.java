package org.sopt.common.auth;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserResponse;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	@Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
	private long refreshTokenExpiresInSeconds;

	public UserResponse loginWithCredentials(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
		}

		return UserResponse.from(user);
	}

	@Transactional
	public TokenResponse login(String email, String password) {
		UserResponse user = loginWithCredentials(email, password);

		String accessToken = jwtService.generateAccessToken(user.id(), user.email());
		String refreshToken = jwtService.generateRefreshToken(user.id());

		// 기존 Refresh Token 삭제 후 새로 저장
		refreshTokenRepository.deleteByUserId(user.id());
		refreshTokenRepository.save(
			RefreshToken.of(user.id(), refreshToken, refreshTokenExpiresInSeconds)
		);

		return TokenResponse.of(accessToken, refreshToken);
	}

	public UserResponse getMemberById(Long memberId) {
		User user = userRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
		return UserResponse.from(user);
	}
}
