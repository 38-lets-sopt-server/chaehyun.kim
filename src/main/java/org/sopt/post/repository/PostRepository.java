package org.sopt.post.repository;

import org.sopt.common.enums.BoardType;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findAllByBoardTypeOrderByCreatedAtDesc(BoardType boardType, Pageable pageable);
}
