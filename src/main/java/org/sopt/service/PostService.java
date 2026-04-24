package org.sopt.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import org.sopt.common.enums.BoardType;
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
			finalAuthor,
			createdAt
		);
		postRepository.save(post);
		return new CreatePostResponse(post.getId());
	}

	public List<PostResponse> getAllPosts(BoardType boardType, int page, int size) {
		BoardType finalBoardType = Optional.ofNullable(boardType).orElse(BoardType.FREE);
		int finalPage = Optional.ofNullable(page).orElse(0);
		int finalSize = Optional.ofNullable(size).orElse(10);

		return postRepository.findAll().stream()
			.filter(post -> post.getBoardType() == finalBoardType)
			.sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
			.skip((long) finalPage * size)
			.limit(finalSize)
			.map(PostResponse::from)
			.collect(Collectors.toList());
	}

	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		return PostResponse.from(post);
	}

	public void updatePost(Long id, String newTitle, String newContent) {
		PostValidator.validateUpdate(newTitle, newContent);

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		post.update(newTitle, newContent);
	}

	public void deletePost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		postRepository.delete(post);
	}
}
