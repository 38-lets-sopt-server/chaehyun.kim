package org.sopt.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.sopt.common.exception.PostNotFoundException;
import org.sopt.common.validator.PostValidator;
import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostService {
	private final PostRepository postRepository;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public CreatePostResponse createPost(CreatePostRequest request) {
		String finalAuthor = request.isAnonymous() ? "익명" : request.author();
		String createdAt = LocalDateTime.now().format(FORMATTER);
		Post post = new Post(
			postRepository.generateId(),
			request.boardType(),
			request.title(),
			request.content(),
			request.author(),
			createdAt
		);
		postRepository.save(post);
		return new CreatePostResponse(post.getId());
	}

	public List<PostResponse> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		List<PostResponse> responses = new ArrayList<>();

		for (Post post : posts) {
			responses.add(new PostResponse(post));
		}

		return responses;
	}

	// READ - 단건 📝 과제
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		return new PostResponse(post);
	}

	// UPDATE 📝 과제
	public void updatePost(Long id, String newTitle, String newContent) {
		PostValidator.validateUpdate(newTitle, newContent);

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		post.update(newTitle, newContent);
	}

	// DELETE 📝 과제
	public void deletePost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		postRepository.delete(post);
	}
}
