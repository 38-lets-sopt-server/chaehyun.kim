package org.sopt.common.exception;

public class PostNotFoundException extends BusinessException {
	public PostNotFoundException(Long id) {
		super(ErrorCode.POST_NOT_FOUND, "해당 게시글이 존재하지 않습니다. ID: " + id);
	}
}
