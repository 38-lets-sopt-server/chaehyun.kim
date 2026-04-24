package org.sopt.common.validator;

import org.sopt.common.exception.BusinessException;
import org.sopt.dto.request.CreatePostRequest;

public class PostValidator {
	private static final int MAX_TITLE_LENGTH = 50;

	public static void validateCreate(CreatePostRequest request) {
		requireNotBlank(request.title(), "제목은 필수입니다.");
		requireValidLength(request.title(), MAX_TITLE_LENGTH, "제목은 50자 이하여야 합니다.");
		requireNotBlank(request.content(), "내용은 필수입니다.");
		requireNotBlank(request.author(), "작성자는 필수입니다.");
	}

	public static void validateUpdate(String title, String content) {
		requireNotBlank(title, "수정할 제목은 필수입니다.");
		requireValidLength(title, MAX_TITLE_LENGTH, "제목은 50자 이하여야 합니다.");
		requireNotBlank(content, "수정할 내용은 필수입니다.");
	}

	private static void requireNotBlank(String value, String message) {
		if (value == null || value.isBlank()) {
			throw BusinessException.badRequest(message);
		}
	}

	private static void requireValidLength(String value, int maxLength, String message) {
		if (value != null && value.length() > maxLength) {
			throw BusinessException.badRequest(message);
		}
	}
}
