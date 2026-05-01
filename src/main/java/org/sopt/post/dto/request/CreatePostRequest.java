package org.sopt.post.dto.request;

import org.sopt.common.enums.BoardType;

public record CreatePostRequest(
	BoardType boardType,
	String title,
	String content,
	String author,
	Boolean isAnonymous
) {
}
