package org.sopt.post.dto.response

data class PostListResponse(
    val posts: List<PostResponse>,
    val totalCount: Long,
    val totalPages: Int,
    val hasNext: Boolean
)
