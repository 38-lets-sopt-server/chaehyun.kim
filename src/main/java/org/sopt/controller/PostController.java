package org.sopt.controller;
import java.util.List;

import org.sopt.common.exception.BusinessException;
import org.sopt.common.exception.PostNotFoundException;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;

public class PostController {
	private final PostService postService = new PostService();

	// POST /posts
	public CreatePostResponse createPost(CreatePostRequest request) {
		try {
			return postService.createPost(request);
		} catch (BusinessException e) {
			return new CreatePostResponse(null, "🚫 " + e.getMessage());
		}
	}

	// GET /posts 📝 과제
	public List<PostResponse> getAllPosts() {
		return postService.getAllPosts();
	}

	// GET /posts/{id} 📝 과제
	public PostResponse getPost(Long id) {
		try {
			return postService.getPost(id);
		} catch (PostNotFoundException e) {
			System.out.println("["+e.getCode()+"] "+ e.getMessage());
			return null;
		}
	}

	// PUT /posts/{id} 📝 과제
	public void updatePost(Long id, String newTitle, String newContent) {
		try{
			postService.updatePost(id, newTitle, newContent);
			System.out.println("게시글이 수정되었습니다.");
		} catch (PostNotFoundException e) {
			System.out.println("["+e.getCode()+"] "+ e.getMessage());
		}
	}

	// DELETE /posts/{id} 📝 과제
	public void deletePost(Long id) {
		try {
			postService.deletePost(id);
			System.out.println("게시글이 삭제되었습니다.");
		} catch (PostNotFoundException e) {
			System.out.println("["+e.getCode()+"] "+ e.getMessage());
		}
	}
}
