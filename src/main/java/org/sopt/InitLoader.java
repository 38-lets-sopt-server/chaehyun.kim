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
			User user1 = userRepository.save(new User("변우석"));
			User user2 = userRepository.save(new User("이계훈"));
			User user3 = userRepository.save(new User("이준호"));

			System.out.println("--------------------------------------");
			System.out.println("테스트 유저 생성 완료");
			System.out.println("유저 1 ID: " + user1.getId() + " (" + user1.getName() + ")");
			System.out.println("유저 2 ID: " + user2.getId() + " (" + user2.getName() + ")");
			System.out.println("유저 3 ID: " + user3.getId() + " (" + user3.getName() + ")");
			System.out.println("--------------------------------------");
		}
	}
}
