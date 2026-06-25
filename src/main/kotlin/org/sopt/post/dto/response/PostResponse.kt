package org.sopt.post.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import org.sopt.common.enums.BoardType
import org.sopt.common.util.DateTimeUtils
import org.sopt.post.domain.Post

data class PostResponse(
    @field:Schema(
        description = "게시글 ID",
        example = "1"
    )
    val id: Long,
    @field:Schema(
        description = "게시판 타입",
        example = "FREE"
    )
    val boardType: BoardType,
    @field:Schema(
        description = "게시글 제목",
        example = "게시글 제목"
    )
    val title: String,
    @field:Schema(
        description = "게시글 내용",
        example = "게시글 내용"
    )
    val content: String,
    @field:Schema(
        description = "작성자 이름",
        example = "홍길동"
    )
    val author: String,
    @field:Schema(
        description = "생성 일시",
        example = "2026-05-01 18:00:00"
    )
    val createdAt: String,
    @field:Schema(
        description = "좋아요 수",
        example = "10"
    )
    val likeCount: Int,
    @field:Schema(
        description = "댓글 수",
        example = "5"
    )
    val commentCount: Int
) {
    constructor(post: Post) : this(
        id = post.id!!,
        boardType = post.boardType,
        title = post.title,
        content = post.content,
        author = if (post.isAnonymous) "익명" else post.user.name,
        createdAt = post.createdAt.format(DateTimeUtils.FORMATTER),
        likeCount = post.likeCount,
        commentCount = post.commentCount
    )
}
