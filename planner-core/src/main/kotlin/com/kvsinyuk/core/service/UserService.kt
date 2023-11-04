package com.kvsinyuk.core.service

import com.kvsinyuk.core.exception.NotFoundException
import com.kvsinyuk.core.model.User
import com.kvsinyuk.core.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(user: User): User =
        userRepository.save(user)

    fun getUserById(id: UUID) =
        userRepository.findById(id)
            .orElseThrow { NotFoundException("User not found") }
}
