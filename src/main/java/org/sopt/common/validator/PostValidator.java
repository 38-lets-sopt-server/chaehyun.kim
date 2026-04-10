package org.sopt.common.validator;

import org.sopt.common.exception.BusinessException;
import org.sopt.dto.request.CreatePostRequest;

public class PostValidator {

	public static void validate(CreatePostRequest request) {
		if (request.title == null || request.title.isBlank()) {
			throw BusinessException.badRequest("제목은 필수입니다!");
		}
		if (request.content == null || request.content.isBlank()) {
			throw BusinessException.badRequest("내용은 필수입니다!");
		}
		if (request.author == null || request.author.isBlank()) {
			throw BusinessException.badRequest("작성자는 필수입니다!");
		}
	}
}
