package org.sopt.post.service;

import java.util.List;

import org.sopt.common.enums.BoardType;
import org.sopt.common.exception.BusinessException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.like.repository.LikeRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final LikeRepository likeRepository;

	public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
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
		Pageable pageable = PageRequest.of(page, size);
		Page<Post> postPage = postRepository.findAllByBoardTypeWithUser(boardType, pageable);

		List<PostResponse> posts = postPage.getContent().stream()
			// .map(post -> {
			// 	long likeCount = likeRepository.countByPost(post);
			// 	return PostResponse.from(post, likeCount);
			// })
			.map(PostResponse::from)
			.toList();

		return PostListResponse.of(
			posts,
			postPage.getTotalElements(),
			postPage.getTotalPages(),
			postPage.hasNext());
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));
		// long likeCount = likeRepository.countByPost(post);

		return PostResponse.from(post);
	}

	@Transactional
	public void updatePost(Long id, UpdatePostRequest request) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		if (!post.getUser().getId().equals(request.userId())) {
			throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
		}

		PostValidator.validateUpdate(request.title(), request.content());
		post.update(request.title(), request.content());
	}

	public void deletePost(Long id, Long userId) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id));

		if (!post.getUser().getId().equals(userId)) {
			throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
		}

		postRepository.delete(post);
	}
}
