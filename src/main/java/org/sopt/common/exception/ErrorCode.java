package org.sopt.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-40000", "잘못된 입력값입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-50000", "서버 내부 오류가 발생했습니다."),

	// POST
	INVALID_POST_TITLE(HttpStatus.BAD_REQUEST, "POST-40001", "게시글 제목은 필수이며 50자 이하여야 합니다."),
	INVALID_POST_CONTENT(HttpStatus.BAD_REQUEST, "POST-40002", "게시글 내용은 필수입니다."),
	HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "POST-40300", "해당 게시글에 대한 권한이 없습니다."),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST-40401", "해당 게시글을 찾을 수 없습니다."),

	// USER
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-40401", "해당 사용자를 찾을 수 없습니다."),
	INVALID_AUTHENTICATION(HttpStatus.BAD_REQUEST, "AUTH-40001", "인증 정보가 누락되었거나 올바르지 않습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-40101", "유효하지 않은 토큰입니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-40102", "만료된 토큰입니다."),
	BLACKLISTED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-40103", "로그아웃된 토큰입니다."),

	// LIKE
	ALREADY_LIKED(HttpStatus.BAD_REQUEST, "LIKE-40001", "이미 좋아요를 누른 게시글입니다."),
	LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "LIKE-40002", "좋아요를 누른 이력이 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getStatus() { return status; }
	public String getCode() { return code; }
	public String getMessage() { return message; }
}
