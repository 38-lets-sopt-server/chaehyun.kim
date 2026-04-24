package org.sopt.common.exception;

public enum ErrorCode {

	// 400
	INVALID_POST_TITLE(40001, "게시글 제목은 필수이며 50자 이하여야 합니다."),
	INVALID_POST_CONTENT(40002, "게시글 내용은 필수입니다."),
	INVALID_POST_AUTHOR(40003, "작성자는 필수입니다."),

	// 404
	POST_NOT_FOUND(40401, "해당 게시글을 찾을 수 없습니다."),
	USER_NOT_FOUND(40402, "해당 사용자를 찾을 수 없습니다.");

	private final int code;
	private final String message;

	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() { return code; }
	public String getMessage() { return message; }
}
