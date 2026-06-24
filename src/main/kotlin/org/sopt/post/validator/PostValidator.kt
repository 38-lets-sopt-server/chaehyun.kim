package org.sopt.post.validator

import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.sopt.post.dto.request.CreatePostRequest

object PostValidator {
    private const val MAX_TITLE_LENGTH = 50

    fun validateCreate(request: CreatePostRequest) {
        requireNotBlank(request.title, ErrorCode.INVALID_POST_TITLE)
        requireValidLength(request.title, MAX_TITLE_LENGTH, ErrorCode.INVALID_POST_TITLE)
        requireNotBlank(request.content, ErrorCode.INVALID_POST_CONTENT)
    }

    fun validateUpdate(title: String, content: String) {
        requireNotBlank(title, ErrorCode.INVALID_POST_TITLE)
        requireValidLength(title, MAX_TITLE_LENGTH, ErrorCode.INVALID_POST_TITLE)
        requireNotBlank(content, ErrorCode.INVALID_POST_CONTENT)
    }

    private fun requireNotBlank(value: String, errorCode: ErrorCode) {
        if (value.isBlank()) {
            throw BusinessException(errorCode)
        }
    }

    private fun requireValidLength(value: String, maxLength: Int, errorCode: ErrorCode) {
        if (value.length > maxLength) {
            throw BusinessException(errorCode)
        }
    }
}
