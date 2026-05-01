package org.sopt.post.repository;

import org.sopt.common.enums.BoardType;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT p FROM Post p " +
		"JOIN FETCH p.user " +
		"WHERE p.boardType = :boardType " +
		"ORDER BY p.createdAt DESC")
	Page<Post> findAllByBoardTypeWithUser(BoardType boardType, Pageable pageable);
}
