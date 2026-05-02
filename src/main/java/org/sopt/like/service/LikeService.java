package org.sopt.like.service;

import java.util.Optional;

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
	public boolean toggleLike(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException(postId));

		Optional<Like> like = likeRepository.findByUserAndPost(user, post);

		if (like.isPresent()) {
			likeRepository.delete(like.get());
			return false;
		} else {
			likeRepository.save(new Like(user, post));
			return true;
		}
	}
}
