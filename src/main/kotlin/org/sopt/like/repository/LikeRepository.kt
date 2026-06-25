package org.sopt.like.repository

import org.sopt.like.domain.Like
import org.sopt.post.domain.Post
import org.sopt.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long> {
    fun findByUserAndPost(user: User?, post: Post?): Like?

    fun countByPost(post: Post): Long
}
