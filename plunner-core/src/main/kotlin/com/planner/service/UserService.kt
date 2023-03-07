package com.planner.service

import com.planner.dto.request.CreateUserRequestDto
import com.planner.exception.NotFoundException
import com.planner.mapper.UserMapper
import com.planner.model.User
import com.planner.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    private val userGroupService: UserGroupService
) {

    @Transactional
    fun createUser(user: CreateUserRequestDto): User {
        val newGroup = userGroupService.createNewGroup()
        val newUser = userMapper.toUser(user)
            .apply { group = newGroup }
            .let { userRepository.save(it) }
        userGroupService.setGroupAdmin(newGroup, newUser)

        return newUser
    }

    fun getUserById(id: Long) =
        userRepository.getById(id)
            ?: throw NotFoundException("User not found")
}
