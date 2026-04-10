package org.sopt.common.exception;

public class BusinessException extends RuntimeException {
	private final String code;

	public BusinessException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() { return code; }

	public static BusinessException notFound(String message) {
		return new BusinessException(message, "NOT_FOUND");
	}

	public static BusinessException badRequest(String message) {
		return new BusinessException(message, "BAD_REQUEST");
	}
}
