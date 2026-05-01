package org.sopt.post.dto.request;

public record UpdatePostRequest (
	Long userId,
	String title,
	String content
){}
