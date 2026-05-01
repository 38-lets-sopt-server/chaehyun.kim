package org.sopt.post.dto.response;

import java.time.LocalDateTime;

import org.sopt.common.enums.BoardType;
import org.sopt.common.util.DateTimeUtils;
import org.sopt.post.domain.Post;

public record PostResponse(
	Long id,
	BoardType boardType,
	String title,
	String content,
	String author,
	String createdAt,
	int likeCount,
	int commentCount
) {
	public static PostResponse from(Post post) {
		return new PostResponse(
			post.getId(),
			post.getBoardType(),
			post.getTitle(),
			post.getContent(),
			post.getIsAnonymous() ? "익명" : post.getAuthorName(),
			post.getCreatedAt().format(DateTimeUtils.FORMATTER),
			post.getLikeCount(),
			post.getCommentCount()
		);
	}
}
