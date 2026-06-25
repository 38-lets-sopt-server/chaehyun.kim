package org.sopt.post.repository

import org.sopt.common.enums.BoardType
import org.sopt.post.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    @Query(
        """
    SELECT p
    FROM Post p
    JOIN FETCH p.user
    WHERE p.boardType = :boardType
    ORDER BY p.createdAt DESC
    """
    )
    fun findAllByBoardTypeWithUser(boardType: BoardType, pageable: Pageable): Page<Post>
}
