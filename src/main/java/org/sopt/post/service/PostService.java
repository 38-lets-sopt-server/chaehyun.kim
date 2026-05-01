package org.sopt.post.service;

import java.util.stream.Collectors;
import java.util.List;

import org.sopt.common.enums.BoardType;
import org.sopt.common.exception.BusinessException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.CreatePostResponse;
import org.sopt.post.dto.response.PostListResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.validator.PostValidator;
import org.sopt.user.domain.User;

import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public CreatePostResponse createPost(CreatePostRequest request) {
		User user = userRepository.findById(request.userId())
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		PostValidator.validateCreate(request);

		Post post = new Post(
			request.boardType(),
			request.title(),
			request.content(),
			user,
			request.isAnonymous()
		);
		postRepository.save(post);
		return new CreatePostResponse(post.getId());
	}

	@Transactional(readOnly = true)
	public PostListResponse getAllPosts(BoardType boardType, int page, int size) {
		List<Post> filteredPosts = postRepository.findAll().stream()
			.filter(post -> post.getBoardType() == boardType)
			.sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
			.toList();

		long totalCount = filteredPosts.size();
		int totalPages = (int) Math.ceil((double) totalCount / size);
		boolean hasNext = totalCount > (long) (page + 1) * size;

		List<PostResponse> posts = filteredPosts.stream()
			.skip((long) page * size)
			.limit(size)
			.map(PostResponse::from)
			.collect(Collectors.toList());

		return PostListResponse.of(posts, totalCount, totalPages, hasNext);
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		return PostResponse.from(post);
	}

	@Transactional
	public void updatePost(Long id, UpdatePostRequest request) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		if (!post.getAuthorName().equals(request.author())) {
			throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
		}

		PostValidator.validateUpdate(request.title(), request.content());
		post.update(request.title(), request.content());
	}

	public void deletePost(Long id, String author) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		if (!post.getAuthorName().equals(author)) {
			throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
		}

		postRepository.delete(post);
	}
}
