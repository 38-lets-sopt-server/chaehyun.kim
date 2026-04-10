package org.sopt.common.validator;

import org.sopt.common.exception.BusinessException;
import org.sopt.dto.request.CreatePostRequest;

public class PostValidator {
	private static final int MAX_TITLE_LENGTH = 50;

	public static void validateCreate(CreatePostRequest request) {
		if (request.getTitle() == null || request.getTitle().isBlank()) {
			throw BusinessException.badRequest("제목은 필수입니다.");
		}
		if (request.getTitle().length() > MAX_TITLE_LENGTH) {
			throw BusinessException.badRequest("제목은 50자 이하여야 합니다.");
		}
		if (request.getContent() == null || request.getContent().isBlank()) {
			throw BusinessException.badRequest("내용은 필수입니다.");
		}
		if (request.getAuthor() == null || request.getAuthor().isBlank()) {
			throw BusinessException.badRequest("작성자는 필수입니다.");
		}
	}

	public static void validateUpdate(String title, String content) {
		if (title == null || title.isBlank()) {
			throw BusinessException.badRequest("수정할 제목은 필수입니다.");
		}
		if (title.length() > MAX_TITLE_LENGTH) {
			throw BusinessException.badRequest("제목은 50자 이하여야 합니다.");
		}
		if (content == null || content.isBlank()) {
			throw BusinessException.badRequest("수정할 내용은 필수입니다.");
		}
	}
}
