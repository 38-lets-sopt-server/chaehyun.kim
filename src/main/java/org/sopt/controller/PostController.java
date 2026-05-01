package org.sopt.controller;

import org.sopt.common.enums.BoardType;
import org.sopt.common.response.CustomAPIResponse;
import org.sopt.common.response.SuccessStatus;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostListResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<CustomAPIResponse<CreatePostResponse>> createPost(
		@RequestBody CreatePostRequest request
	) {
		CreatePostResponse response = postService.createPost(request);
		return ResponseEntity
			.status(SuccessStatus.CREATE_POST_SUCCESS.getStatus())
			.body(CustomAPIResponse.createSuccess(SuccessStatus.CREATE_POST_SUCCESS, response));
	}

	@GetMapping
	public ResponseEntity<CustomAPIResponse<PostListResponse>> getAllPosts(
		@RequestParam(defaultValue = "FREE") BoardType boardType,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		PostListResponse response = postService.getAllPosts(boardType, page, size);

		return ResponseEntity
			.status(SuccessStatus.GET_POST_LIST_SUCCESS.getStatus())
			.body(CustomAPIResponse.createSuccess(SuccessStatus.GET_POST_LIST_SUCCESS, response));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomAPIResponse<PostResponse>> getPost(@PathVariable Long id) {
		PostResponse response = postService.getPost(id);

		return ResponseEntity
			.status(SuccessStatus.GET_POST_SUCCESS.getStatus())
			.body(CustomAPIResponse.createSuccess(SuccessStatus.GET_POST_SUCCESS, response));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomAPIResponse<Void>> updatePost(
		@PathVariable Long id,
		@RequestBody UpdatePostRequest request
	) {
		postService.updatePost(id, request);

		return ResponseEntity
			.status(SuccessStatus.UPDATE_POST_SUCCESS.getStatus())
			.body(CustomAPIResponse.createSuccess(SuccessStatus.UPDATE_POST_SUCCESS, null));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomAPIResponse<Void>> deletePost(
		@PathVariable Long id,
		@RequestParam String author
	) {
		postService.deletePost(id, author);

		return ResponseEntity
			.status(SuccessStatus.DELETE_POST_SUCCESS.getStatus())
			.body(CustomAPIResponse.createSuccess(SuccessStatus.DELETE_POST_SUCCESS, null));
	}
}
