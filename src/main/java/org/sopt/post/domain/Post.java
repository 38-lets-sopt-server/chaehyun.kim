package org.sopt.post.domain;

import java.time.LocalDateTime;

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

	private int commentCount;
	private int likeCount;
	private Boolean isAnonymous;

	public Post(BoardType boardType, String title, String content, User user, Boolean isAnonymous) {
		this.boardType = boardType;
		this.title = title;
		this.content = content;
		this.commentCount = 0;
		this.likeCount = 0;
		this.user = user;
		this.isAnonymous = isAnonymous;
	}

	public Long getId() { return id; }

	public BoardType getBoardType() { return boardType; }

	public String getTitle() { return title; }

	public String getContent() { return content; }

	public String getAuthorName() { return this.user.getName(); }

	public int getCommentCount() { return commentCount; }

	public int getLikeCount() { return likeCount; }

	public Boolean getIsAnonymous() {  return isAnonymous; }

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// TODO: 좋아요, 댓글 로직 구현
	public void increaseLikeCount() { this.likeCount++; }
	public void increaseCommentCount() { this.commentCount++; }

}
