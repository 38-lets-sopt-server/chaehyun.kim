package org.sopt.like.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.sopt.common.auth.resolver.UserId
import org.sopt.common.response.CustomAPIResponse
import org.sopt.common.response.SuccessStatus
import org.sopt.like.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequestMapping("/likes")
class LikeController(private val likeService: LikeService) {
    @Operation(summary = "좋아요 변경", description = "좋아요가 없으면 생성하고, 있으면 삭제합니다.")
    @PatchMapping("/posts/{postId}")
    fun toggleLike(
        @PathVariable postId: Long,
        @UserId userId: Long
    ): ResponseEntity<CustomAPIResponse<Void?>> {
        val isLiked = likeService.toggleLike(userId, postId)

        val status = if (isLiked) SuccessStatus.CLICK_LIKE_SUCCESS else SuccessStatus.CANCEL_LIKE_SUCCESS

        return ResponseEntity
            .status(status.status)
            .body(CustomAPIResponse.createSuccess(status, null))
    }
}
