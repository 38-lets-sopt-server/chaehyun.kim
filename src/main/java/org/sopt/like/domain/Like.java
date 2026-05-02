package org.sopt.like.domain;

import org.sopt.common.domain.BaseTimeEntity;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	protected Like() {}

	public Like( User user, Post post) {
		this.user = user;
		this.post = post;
	}
}
