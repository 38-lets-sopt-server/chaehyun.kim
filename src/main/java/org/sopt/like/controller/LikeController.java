package org.sopt.like.controller;

import org.sopt.common.response.CustomAPIResponse;
import org.sopt.common.response.SuccessStatus;
import org.sopt.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequestMapping("/likes")
public class LikeController {
	private final LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@Operation(summary = "좋아요 변경", description = "좋아요가 없으면 생성하고, 있으면 삭제합니다.")
	@PatchMapping("/posts/{postId}") // PATCH 사용!
	public ResponseEntity<CustomAPIResponse<Void>> toggleLike(
		@PathVariable Long postId,
		Authentication authentication
	) {
		Long userId = Long.parseLong(authentication.getName());

		boolean isLiked = likeService.toggleLike(userId, postId);

		SuccessStatus status = isLiked ? SuccessStatus.CLICK_LIKE_SUCCESS : SuccessStatus.CANCEL_LIKE_SUCCESS;

		return ResponseEntity
			.status(status.getStatus())
			.body(CustomAPIResponse.createSuccess(status, null));
	}
}
