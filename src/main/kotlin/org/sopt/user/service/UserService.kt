package org.sopt.user.service

import org.sopt.user.domain.User
import org.sopt.user.dto.UserCreateRequest
import org.sopt.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {
    @Transactional
    fun join(request: UserCreateRequest): Long? {
        val encodedPassword = passwordEncoder.encode(request.password)

        val user = User(request.name, request.email, encodedPassword)

        val savedUser = userRepository.save(user)

        return savedUser.id
    }
}
