package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserCreateRequest;
import org.sopt.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Long join(UserCreateRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password);

		User user = new User(request.name, request.email, encodedPassword);

		User savedUser = userRepository.save(user);

		return savedUser.id;
	}
}
