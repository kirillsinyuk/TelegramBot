package com.kvsinyuk.core.service

import com.kvsinyuk.core.exception.NotFoundException
import com.kvsinyuk.core.model.User
import com.kvsinyuk.core.repository.UserRepository
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
