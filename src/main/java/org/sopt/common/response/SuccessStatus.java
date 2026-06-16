package org.sopt.common.response;

import org.springframework.http.HttpStatus;

public enum SuccessStatus {
	LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
	LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),
	GET_USER_PROFILE_SUCCESS(HttpStatus.OK, "내 정보 조회 성공"),

	CREATE_POST_SUCCESS(HttpStatus.CREATED, "게시글 작성 성공"),
	GET_POST_LIST_SUCCESS(HttpStatus.OK, "게시글 목록 조회 성공"),
	GET_POST_SUCCESS(HttpStatus.OK, "게시글 조회 성공"),
	UPDATE_POST_SUCCESS(HttpStatus.NO_CONTENT, "게시글 수정 성공"),
	DELETE_POST_SUCCESS(HttpStatus.NO_CONTENT, "게시글 삭제 성공"),

	CLICK_LIKE_SUCCESS(HttpStatus.OK, "좋아요가 반영되었습니다."),
	CANCEL_LIKE_SUCCESS(HttpStatus.OK, "좋아요가 취소되었습니다.");

	private final HttpStatus status;
	private final String message;

	SuccessStatus(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() { return status; }
	public String getMessage() { return message; }
}
