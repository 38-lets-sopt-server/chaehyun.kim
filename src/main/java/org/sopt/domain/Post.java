package org.sopt.domain;

public class Post {
	private final Long id;
	private String title;
	private String content;
	private String author;
	private final String createdAt;
	private int commentCount;
	private int likeCount;

	public Post(Long id, String title, String content, String author, String createdAt, int commentCount, int likeCount) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = createdAt;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
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

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getInfo() {
		return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content;
	}
}
