package org.sopt.post.service

import org.sopt.common.enums.BoardType
import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.sopt.like.repository.LikeRepository
import org.sopt.post.domain.Post
import org.sopt.post.dto.request.CreatePostRequest
import org.sopt.post.dto.request.UpdatePostRequest
import org.sopt.post.dto.response.CreatePostResponse
import org.sopt.post.dto.response.PostListResponse
import org.sopt.post.dto.response.PostResponse
import org.sopt.post.exception.PostNotFoundException
import org.sopt.post.repository.PostRepository
import org.sopt.post.validator.PostValidator.validateCreate
import org.sopt.post.validator.PostValidator.validateUpdate
import org.sopt.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val likeRepository: LikeRepository
) {
    @Transactional
    fun createPost(request: CreatePostRequest, userId: Long): CreatePostResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }

        validateCreate(request)

        val post = Post(
            request.boardType,
            request.title,
            request.content,
            user,
            request.isAnonymous
        )
        val savedPost = postRepository.save(post)
        return CreatePostResponse(savedPost.id!!)
    }

    @Transactional(readOnly = true)
    fun getAllPosts(boardType: BoardType, page: Int, size: Int): PostListResponse {
        val pageable = PageRequest.of(page, size)
        val postPage = postRepository.findAllByBoardTypeWithUser(boardType, pageable)

        val posts = postPage.content.map(::PostResponse)

        return PostListResponse(
            posts,
            postPage.totalElements,
            postPage.totalPages,
            postPage.hasNext()
        )
    }

    @Transactional(readOnly = true)
    fun getPost(id: Long): PostResponse {
        val post = postRepository.findById(id)
            .orElseThrow { PostNotFoundException(id) }

        // long likeCount = likeRepository.countByPost(post);
        return PostResponse(post)
    }

    @Transactional
    fun updatePost(id: Long, request: UpdatePostRequest, userId: Long) {
        val post = postRepository.findById(id)
            .orElseThrow { PostNotFoundException(id) }

        if (post.user.id != userId) {
            throw BusinessException(ErrorCode.HANDLE_ACCESS_DENIED)
        }

        validateUpdate(request.title, request.content)
        post.update(request.title, request.content)
    }

    @Transactional
    fun deletePost(id: Long, userId: Long) {
        val post = postRepository.findById(id)
            .orElseThrow { PostNotFoundException(id) }

        if (post.user.id != userId) {
            throw BusinessException(ErrorCode.HANDLE_ACCESS_DENIED)
        }

        postRepository.delete(post)
    }
}
