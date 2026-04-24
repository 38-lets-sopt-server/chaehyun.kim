package org.sopt.common.validator;

import org.sopt.common.exception.BusinessException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.dto.request.CreatePostRequest;

public class PostValidator {
	private static final int MAX_TITLE_LENGTH = 50;

	public static void validateCreate(CreatePostRequest request) {
		requireNotNull(request.boardType(), ErrorCode.INVALID_INPUT_VALUE);
		requireNotNull(request.isAnonymous(), ErrorCode.INVALID_INPUT_VALUE);
		requireNotBlank(request.title(), ErrorCode.INVALID_POST_TITLE);
		requireValidLength(request.title(), MAX_TITLE_LENGTH, ErrorCode.INVALID_POST_TITLE);
		requireNotBlank(request.content(), ErrorCode.INVALID_POST_CONTENT);
		requireNotBlank(request.author(), ErrorCode.INVALID_POST_AUTHOR);
	}

	private static void requireNotNull(Object value, ErrorCode errorCode) {
		if (value == null) {
			throw new BusinessException(errorCode);
		}
	}

	public static void validateUpdate(String title, String content) {
		requireNotBlank(title, ErrorCode.INVALID_POST_TITLE);
		requireValidLength(title, MAX_TITLE_LENGTH, ErrorCode.INVALID_POST_TITLE);
		requireNotBlank(content, ErrorCode.INVALID_POST_CONTENT);
	}

	private static void requireNotBlank(String value, ErrorCode errorCode) {
		if (value == null || value.isBlank()) {
			throw new BusinessException(errorCode);
		}
	}

	private static void requireValidLength(String value, int maxLength, ErrorCode errorCode) {
		if (value != null && value.length() > maxLength) {
			throw new BusinessException(errorCode);
		}
	}
}
