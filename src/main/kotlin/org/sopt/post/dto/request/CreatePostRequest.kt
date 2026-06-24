package org.sopt.post.dto.request

import org.sopt.common.enums.BoardType

data class CreatePostRequest(
    val boardType: BoardType,
    val title: String,
    val content: String,
    val isAnonymous: Boolean
)
