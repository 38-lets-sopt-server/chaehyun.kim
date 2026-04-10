package org.sopt.dto.response;

// 게시글 작성 응답 (서버 → 클라이언트)
public class CreatePostResponse {
	private Long id;
	private String message;

	public CreatePostResponse(Long id, String message) {
		this.id = id;
		this.message = message;
	}
}
