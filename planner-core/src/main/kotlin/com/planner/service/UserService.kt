package com.planner.service

import com.planner.exception.NotFoundException
import com.planner.model.User
import com.planner.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(user: User): User =
        userRepository.save(user)

    fun getUserById(id: Long) =
        userRepository.getById(id)
            ?: throw NotFoundException("User not found")
}
