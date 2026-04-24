package org.sopt.controller;
import java.util.List;

import org.sopt.common.response.CustomAPIResponse;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<CustomAPIResponse<CreatePostResponse>> createPost(
		@RequestBody CreatePostRequest request
	) {
		CreatePostResponse response = postService.createPost(request);
		return ResponseEntity.ok(
			CustomAPIResponse.createSuccess(
				HttpStatus.CREATED.value(),
				"게시글 작성 성공",
				response
			)
		);
	}

	@GetMapping
	public ResponseEntity<CustomAPIResponse<List<PostResponse>>> getAllPosts() {
		List<PostResponse> response = postService.getAllPosts();
		return ResponseEntity.ok(
			CustomAPIResponse.createSuccess(
				HttpStatus.OK.value(),
				"게시글 목록 조회 성공",
				response
			)
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomAPIResponse<PostResponse>> getPost(@PathVariable Long id) {
		PostResponse response = postService.getPost(id);
		return ResponseEntity.ok(
			CustomAPIResponse.createSuccess(
				HttpStatus.OK.value(),
				"게시글 조회 성공",
				response
			)
		);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomAPIResponse<Void>> updatePost(
		@PathVariable Long id,
		@RequestBody UpdatePostRequest request
	) {
		postService.updatePost(id, request.title(), request.content());

		return ResponseEntity.ok(
			CustomAPIResponse.createSuccess(
				HttpStatus.OK.value(),
				"게시글 수정 성공",
				null
			)
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomAPIResponse<Void>> deletePost(@PathVariable Long id) {
		postService.deletePost(id);

		return ResponseEntity.ok(
			CustomAPIResponse.createSuccess(
				HttpStatus.OK.value(),
				"리워드 계좌 변경 성공",
				null
			)
		);
	}
}
