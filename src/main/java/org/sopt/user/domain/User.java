package org.sopt.user.domain;

import jakarta.persistence.*;
import org.sopt.common.domain.BaseTimeEntity;

@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String password;

	private String name;

	protected User() {}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
}
