package org.sopt;

import org.sopt.user.domain.User;

import org.sopt.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.lang.Nullable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitLoader implements ApplicationRunner {
	private final UserRepository userRepository;

	public InitLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(@Nullable ApplicationArguments args) {
		if (userRepository.count() == 0) {
			User testUser = new User("홍길동");
			userRepository.save(testUser);

			System.out.println("--------------------------------------");
			System.out.println("테스트 유저 생성 완료 (ID: " + testUser.getId() + ")");
			System.out.println("--------------------------------------");
		}
	}
}
