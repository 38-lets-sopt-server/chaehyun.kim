package org.sopt.post.dto.response;

import org.sopt.common.enums.BoardType;
import org.sopt.common.util.DateTimeUtils;
import org.sopt.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostResponse(
	@Schema(description = "게시글 ID", example = "1")
	Long id,
	@Schema(description = "게시판 타입", example = "FREE")
	BoardType boardType,
	@Schema(description = "게시글 제목", example = "게시글 제목")
	String title,
	@Schema(description = "게시글 내용", example = "게시글 내용")
	String content,
	@Schema(description = "작성자 이름", example = "홍길동")
	String author,
	@Schema(description = "생성 일시", example = "2026-05-01 18:00:00")
	String createdAt,
	@Schema(description = "좋아요 수", example = "10")
	int likeCount,
	@Schema(description = "댓글 수", example = "5")
	int commentCount
) {
	public static PostResponse from(Post post, long likeCount) {
		return new PostResponse(
			post.getId(),
			post.getBoardType(),
			post.getTitle(),
			post.getContent(),
			post.getIsAnonymous() ? "익명" : post.getUser().getName(),
			post.getCreatedAt().format(DateTimeUtils.FORMATTER),
			(int) likeCount,
			post.getCommentCount()
		);
	}
}
