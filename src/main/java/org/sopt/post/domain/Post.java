package org.sopt.post.domain;

import java.time.LocalDateTime;

import org.sopt.common.enums.BoardType;

public class Post {
	private final Long id;
	private final BoardType boardType;
	private String title;
	private String content;
	private String author;
	private final LocalDateTime createdAt;
	private int commentCount;
	private int likeCount;
	private Boolean isAnonymous;

	public Post(Long id, BoardType boardType, String title, String content, String author, LocalDateTime createdAt, Boolean isAnonymous) {
		this.id = id;
		this.boardType = boardType;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = createdAt;
		this.isAnonymous = isAnonymous;
		this.commentCount = 0;
		this.likeCount = 0;
	}

	public Long getId() {
		return id;
	}

	public BoardType getBoardType() { return boardType; }

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public Boolean getIsAnonymous() {  return isAnonymous; }

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// TODO: 좋아요, 댓글 로직 구현
	public void increaseLikeCount() { this.likeCount++; }
	public void increaseCommentCount() { this.commentCount++; }
}
