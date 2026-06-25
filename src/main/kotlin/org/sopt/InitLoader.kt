package org.sopt

import org.sopt.user.domain.User
import org.sopt.user.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class InitLoader(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) :
    ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        if (userRepository.count() > 0) return

        val defaultPassword = passwordEncoder.encode("password123!")

        val user1 = userRepository.save(User("변우석", "wooseok@sopt.org", defaultPassword))
        val user2 = userRepository.save(User("이계훈", "gyehun@sopt.org", defaultPassword))
        val user3 = userRepository.save(User("이준호", "junho@sopt.org", defaultPassword))

        println("--------------------------------------")
        println("테스트 유저 생성 완료")
        println("유저 1 ID: ${user1.id} (${user1.name})")
        println("유저 2 ID: ${user2.id} (${user2.name})")
        println("유저 3 ID: ${user3.id} (${user3.name})")
        println("--------------------------------------")
    }
}
