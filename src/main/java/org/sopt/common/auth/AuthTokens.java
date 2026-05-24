package org.sopt.common.auth;

public record AuthTokens(
	String accessToken,
	String refreshToken
) {}
