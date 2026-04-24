package org.sopt.common.exception;

import org.sopt.common.response.CustomAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<CustomAPIResponse<?>> handleBusinessException(BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();

		return ResponseEntity
			.status(HttpStatus.valueOf(errorCode.getCode() / 100))
			.body(CustomAPIResponse.createFail(
				errorCode.getCode(),
				e.getMessage()
			));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomAPIResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(CustomAPIResponse.createFail(
				ErrorCode.INVALID_INPUT_VALUE.getCode(),
				e.getMessage()
			));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CustomAPIResponse<?>> handleIllegalArgument(IllegalArgumentException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(CustomAPIResponse.createFail(
				ErrorCode.INVALID_INPUT_VALUE.getCode(),
				e.getMessage()
			));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomAPIResponse<?>> handleException(Exception e) {
		System.err.println("Unhandled Exception: " + e.getMessage());

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(CustomAPIResponse.createFail(
				ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
				ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
			));
	}
}
