package org.sopt.common.exception.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.sopt.common.exception.BusinessException
import org.sopt.common.exception.ErrorCode
import org.sopt.common.response.CustomAPIResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<CustomAPIResponse<*>> {
        val errorCode = e.errorCode

        return ResponseEntity
            .status(errorCode.status)
            .body(
                CustomAPIResponse.createFail(
                    errorCode.code,
                    e.message ?: errorCode.message
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<CustomAPIResponse<*>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                CustomAPIResponse.createFail(
                    ErrorCode.INVALID_INPUT_VALUE.code,
                    e.message ?: ErrorCode.INVALID_INPUT_VALUE.message
                )
            )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<CustomAPIResponse<*>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                CustomAPIResponse.createFail(
                    ErrorCode.INVALID_INPUT_VALUE.code,
                    e.message ?: ErrorCode.INVALID_INPUT_VALUE.message
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<CustomAPIResponse<*>> {
        log.error(e) { "Unhandled Exception" }

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                CustomAPIResponse.createFail(
                    ErrorCode.INTERNAL_SERVER_ERROR.code,
                    ErrorCode.INTERNAL_SERVER_ERROR.message
                )
            )
    }
}
