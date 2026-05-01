package org.sopt.like.service;

import org.sopt.common.exception.BusinessException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.like.domain.Like;
import org.sopt.like.repository.LikeRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
	private final LikeRepository likeRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
		this.likeRepository = likeRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public void addLike(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException(postId));

		if (likeRepository.existsByUserAndPost(user, post)) {
			throw new BusinessException(ErrorCode.ALREADY_LIKED);
		}

		likeRepository.save(new Like(user, post));
		// TODO: Post에도 반영하기
	}

	@Transactional
	public void cancelLike(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException(postId));

		Like like = likeRepository.findByUserAndPost(user, post)
			.orElseThrow(() -> new BusinessException(ErrorCode.LIKE_NOT_FOUND));

		likeRepository.delete(like);
		// TODO: Post에도 반영하기
	}
}
