package com.kvsinyuk.core.adapter.`in`.http

import com.kvsinyuk.core.adapter.mapper.UserMapper
import com.kvsinyuk.core.application.port.`in`.CreateUserPort
import com.kvsinyuk.core.application.port.`in`.GetUserPort
import com.kvsinyuk.v1.http.UserApiProto.CreateUserRequest
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
    private val createUserPort: CreateUserPort,
    private val getUserPort: GetUserPort,
    private val userMapper: UserMapper
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest) =
        createUserPort.createUser(request)
            .let { userMapper.toCreateUserResponse(it) }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) =
        getUserPort.getUserById(userId)
            .let { userMapper.toGetUserResponse(it) }
}
