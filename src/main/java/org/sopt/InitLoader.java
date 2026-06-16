package org.sopt;

import org.sopt.user.domain.User;

import org.sopt.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.lang.Nullable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitLoader implements ApplicationRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public InitLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(@Nullable ApplicationArguments args) {
		if (userRepository.count() == 0) {
			String defaultPassword = passwordEncoder.encode("password123!");

			User user1 = userRepository.save(new User("변우석", "wooseok@sopt.org", defaultPassword));
			User user2 = userRepository.save(new User("이계훈", "gyehun@sopt.org", defaultPassword));
			User user3 = userRepository.save(new User("이준호", "junho@sopt.org", defaultPassword));

			System.out.println("--------------------------------------");
			System.out.println("테스트 유저 생성 완료");
			System.out.println("유저 1 ID: " + user1.id + " (" + user1.getName() + ")");
			System.out.println("유저 2 ID: " + user2.id + " (" + user2.getName() + ")");
			System.out.println("유저 3 ID: " + user3.id + " (" + user3.getName() + ")");
			System.out.println("--------------------------------------");
		}
	}
}
