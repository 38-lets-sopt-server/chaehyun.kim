package org.sopt.like.repository;

import java.util.Optional;

import org.sopt.like.domain.Like;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByUserAndPost(User user, Post post);

	long countByPost(Post post);
}
