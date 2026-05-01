package org.sopt.post.dto.response;

import java.util.List;

public record PostListResponse(
	List<PostResponse> posts,
	long totalCount,
	int totalPages,
	boolean hasNext
) {
	public static PostListResponse of(List<PostResponse> posts, long totalCount, int totalPages, boolean hasNext) {
		return new PostListResponse(posts, totalCount,totalPages, hasNext);
	}
}
