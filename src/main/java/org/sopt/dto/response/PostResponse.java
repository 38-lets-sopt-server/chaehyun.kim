package org.sopt.dto.response;

import org.sopt.domain.Post;

public class PostResponse {
	private final Long id;
	private final String title;
	private final String content;
	private final String author;
	private final String createdAt;
	private final int commentCount;
	private final int likeCount;

	public PostResponse(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.author = post.getAuthor();
		this.createdAt = post.getCreatedAt();
		this.commentCount = post.getCommentCount();
		this.likeCount = post.getLikeCount();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	@Override
	public String toString() {
		return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content + "\n"
			+ "좋아요: " + likeCount + ", 댓글: " + commentCount;
	}
}
