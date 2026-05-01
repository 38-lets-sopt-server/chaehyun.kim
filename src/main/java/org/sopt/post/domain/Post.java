package org.sopt.post.domain;

import org.hibernate.annotations.Formula;
import org.sopt.common.domain.BaseTimeEntity;
import org.sopt.common.enums.BoardType;
import org.sopt.user.domain.User;

import jakarta.persistence.*;

import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;

@Entity
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private BoardType boardType;
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	protected Post() {}

	@Formula("(select count(1) from likes l where l.post_id = id)")
	private int likeCount;
	private int commentCount;
	private Boolean isAnonymous;

	public Post(BoardType boardType, String title, String content, User user, Boolean isAnonymous) {
		this.boardType = boardType;
		this.title = title;
		this.content = content;
		this.commentCount = 0;
		this.user = user;
		this.isAnonymous = isAnonymous;
	}

	public Long getId() { return id; }

	public BoardType getBoardType() { return boardType; }

	public String getTitle() { return title; }

	public String getContent() { return content; }

	public User getUser() { return user; }

	public int getLikeCount() { return likeCount; }

	public int getCommentCount() { return commentCount; }

	public Boolean getIsAnonymous() {  return isAnonymous; }

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// TODO: 댓글 로직 구현
	public void increaseCommentCount() { this.commentCount++; }

}
