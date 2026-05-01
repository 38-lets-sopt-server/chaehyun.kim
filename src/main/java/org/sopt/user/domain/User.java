package org.sopt.user.domain;

import jakarta.persistence.*;
import org.sopt.common.domain.BaseTimeEntity;

@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	protected User() {}

	public User(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
