package org.sopt.post.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.sopt.common.auth.resolver.UserId
import org.sopt.common.enums.BoardType
import org.sopt.common.response.CustomAPIResponse
import org.sopt.common.response.SuccessStatus
import org.sopt.post.dto.request.CreatePostRequest
import org.sopt.post.dto.request.UpdatePostRequest
import org.sopt.post.dto.response.CreatePostResponse
import org.sopt.post.dto.response.PostListResponse
import org.sopt.post.dto.response.PostResponse
import org.sopt.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @PostMapping
    fun createPost(
        @RequestBody request: CreatePostRequest,
        @UserId userId: Long
    ): ResponseEntity<CustomAPIResponse<CreatePostResponse>> {
        val response = postService.createPost(request, userId)
        return ResponseEntity
            .status(SuccessStatus.CREATE_POST_SUCCESS.status)
            .body(CustomAPIResponse.createSuccess(SuccessStatus.CREATE_POST_SUCCESS, response))
    }

    @Operation(summary = "게시글 목록 조회", description = "게시판 타입별로 페이징된 게시글 목록을 조회합니다.")
    @GetMapping
    fun getAllPosts(
        @Parameter(description = "게시판 타입 (기본값: FREE)") @RequestParam(defaultValue = "FREE") boardType: BoardType,
        @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "한 페이지당 게시글 수") @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<CustomAPIResponse<PostListResponse>> {
        val response = postService.getAllPosts(boardType, page, size)

        return ResponseEntity
            .status(SuccessStatus.GET_POST_LIST_SUCCESS.status)
            .body(CustomAPIResponse.createSuccess(SuccessStatus.GET_POST_LIST_SUCCESS, response))
    }

    @Operation(summary = "게시글 상세 조회", description = "특정 ID의 게시글 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    fun getPost(@Parameter(description = "조회할 게시글 ID") @PathVariable id: Long): ResponseEntity<CustomAPIResponse<PostResponse>> {
        val response = postService.getPost(id)

        return ResponseEntity
            .status(SuccessStatus.GET_POST_SUCCESS.status)
            .body(CustomAPIResponse.createSuccess(SuccessStatus.GET_POST_SUCCESS, response))
    }

    @Operation(summary = "게시글 수정", description = "게시글의 제목과 내용을 수정합니다.")
    @PutMapping("/{id}")
    fun updatePost(
        @Parameter(description = "수정할 게시글 ID") @PathVariable id: Long,
        @RequestBody request: UpdatePostRequest,
        @UserId userId: Long
    ): ResponseEntity<CustomAPIResponse<Void?>> {
        postService.updatePost(id, request, userId)

        return ResponseEntity
            .status(SuccessStatus.UPDATE_POST_SUCCESS.status)
            .body(CustomAPIResponse.createSuccess(SuccessStatus.UPDATE_POST_SUCCESS, null))
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deletePost(
        @Parameter(description = "삭제할 게시글 ID") @PathVariable id: Long,
        @UserId userId: Long
    ): ResponseEntity<CustomAPIResponse<Void?>> {
        postService.deletePost(id, userId)

        return ResponseEntity
            .status(SuccessStatus.DELETE_POST_SUCCESS.status)
            .body(CustomAPIResponse.createSuccess(SuccessStatus.DELETE_POST_SUCCESS, null))
    }
}
