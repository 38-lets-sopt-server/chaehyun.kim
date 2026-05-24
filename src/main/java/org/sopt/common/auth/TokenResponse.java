package org.sopt.common.auth;

public record TokenResponse(
	String accessToken
) {

	public static TokenResponse of(String accessToken) {
		return new TokenResponse(accessToken);
	}
}
