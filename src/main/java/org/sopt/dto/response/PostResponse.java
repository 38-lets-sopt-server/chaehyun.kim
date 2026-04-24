package org.sopt.dto.response;

import org.sopt.common.enums.BoardType;
import org.sopt.domain.Post;
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
			post.getAuthor(),
			post.getCreatedAt(),
			post.getLikeCount(),
			post.getCommentCount()
		);
	}
}
