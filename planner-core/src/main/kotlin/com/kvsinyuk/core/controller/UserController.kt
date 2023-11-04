package com.kvsinyuk.core.controller

import com.kvsinyuk.core.mapper.UserMapper
import com.kvsinyuk.core.service.CreateUserService
import com.kvsinyuk.core.service.UserService
import com.kvsinyuk.plannercoreapi.model.request.CreateUserRequestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userCreateService: CreateUserService,
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequestDto) =
        userCreateService.createUser(request)
            .let { userMapper.toCreateUserResponse(it) }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) =
        userService.getUserById(userId)
            .let { userMapper.toGetUserResponse(it) }
}
