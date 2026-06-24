package org.sopt.like.service

import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.sopt.like.domain.Like
import org.sopt.like.repository.LikeRepository
import org.sopt.post.exception.PostNotFoundException
import org.sopt.post.repository.PostRepository
import org.sopt.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun toggleLike(userId: Long, postId: Long): Boolean {
        val user = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        val post = postRepository.findById(postId)
            .orElseThrow { PostNotFoundException(postId) }

        return likeRepository.findByUserAndPost(user, post)
            ?.let {
                likeRepository.delete(it)
                false
            }
            ?: run {
                likeRepository.save(Like(user, post))
                true
            }
    }
}
