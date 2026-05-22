package org.sopt.user.dto;

public record UserCreateRequest(
	String name,
	String email,
	String password
) {
}
