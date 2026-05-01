package org.sopt.common.response;

public record CustomAPIResponse<T>(
	String status,
	String message,
	T data
) {
	// 성공
	public static <T> CustomAPIResponse<T> createSuccess(SuccessStatus status, T data) {
		return new CustomAPIResponse<T>(String.valueOf(status.getStatus().value()), status.getMessage(), data);
	}

	// 실패
	public static <T> CustomAPIResponse<T> createFail(String status, String message) {
		return new CustomAPIResponse<T>(status, message, null);
	}

	public static <T> CustomAPIResponse<T> createFail(String status, String message, T data) {
		return new CustomAPIResponse<>(status,message, data);
	}
}
