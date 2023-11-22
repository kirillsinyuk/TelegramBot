package com.kvsinyuk.core.service

import com.kvsinyuk.core.mapper.UserMapper
import com.kvsinyuk.v1.http.UserApiProto.CreateUserRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val userMapper: UserMapper,
    private val userService: UserService,
    private val categoryService: CategoryService
) {

    @Transactional
    fun createUser(request: CreateUserRequest) =
        userMapper.toUser(request)
            .let { userService.createUser(it) }
            .also{ categoryService.createDefaultCategories(it) }
}
