package org.sopt.controller;
import java.util.List;

import org.sopt.common.exception.PostNotFoundException;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: 2차 과제에서 GlobalExceptionHandler 만들기
@RestController
@RequestMapping("/posts")
public class PostController {
	private PostService postService;

	// POST /posts
	@PostMapping
	public ResponseEntity<CreatePostResponse> createPost(
		@RequestBody CreatePostRequest request
	) {
		CreatePostResponse response = postService.createPost(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// GET /posts 📝 과제
	@GetMapping
	public List<PostResponse> getAllPosts() {
		return postService.getAllPosts();
	}

	// GET /posts/{id} 📝 과제
	@GetMapping
	public PostResponse getPost(Long id) {
		try {
			return postService.getPost(id);
		} catch (PostNotFoundException e) {
			System.out.println("["+e.getErrorCode().getCode()+"] "+ e.getMessage());
			return null;
		}
	}

	// PUT /posts/{id} 📝 과제
	@PutMapping
	public void updatePost(Long id, String newTitle, String newContent) {
		try{
			postService.updatePost(id, newTitle, newContent);
			System.out.println("게시글이 수정되었습니다.");
		} catch (PostNotFoundException e) {
			System.out.println("["+e.getErrorCode().getCode()+"] "+ e.getMessage());
		}
	}

	// DELETE /posts/{id} 📝 과제
	@DeleteMapping
	public void deletePost(Long id) {
		try {
			postService.deletePost(id);
			System.out.println("게시글이 삭제되었습니다.");
		} catch (PostNotFoundException e) {
			System.out.println("["+e.getErrorCode().getCode()+"] "+ e.getMessage());
		}
	}
}
