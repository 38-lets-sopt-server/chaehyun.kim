package org.sopt.common.exception;

public class PostNotFoundException extends BusinessException {
	public PostNotFoundException(Long id) {
		super("해당 게시글이 존재하지 않습니다. " + id, "NOT_FOUND");
	}
}
