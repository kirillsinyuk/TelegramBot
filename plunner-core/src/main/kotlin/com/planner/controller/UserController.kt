package com.planner.controller

import com.planner.dto.request.CreateUserRequestDto
import com.planner.mapper.UserMapper
import com.planner.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequestDto) =
        userService.createUser(request)
            .let { userMapper.toCreateUserResponse(it) }
}
