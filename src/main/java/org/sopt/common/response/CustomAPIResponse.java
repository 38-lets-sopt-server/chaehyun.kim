package org.sopt.common.response;

public record CustomAPIResponse<T>(
	int status,
	String message,
	T data
) {
	// 성공
	public static <T> CustomAPIResponse<T> createSuccess(int status, String message, T data) {
		return new CustomAPIResponse<T>(status, message, data);
	}

	// 실패
	public static <T> CustomAPIResponse<T> createFail(int status, String message) {
		return new CustomAPIResponse<T>(status, message, null);
	}

	public static <T> CustomAPIResponse<T> createFail(int status, String message, T data) {
		return new CustomAPIResponse<>(status,message, data);
	}
}
