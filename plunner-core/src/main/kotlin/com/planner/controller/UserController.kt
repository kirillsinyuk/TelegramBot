package com.planner.controller

import com.planner.dto.request.CreateUserRequestDto
import com.planner.mapper.CategoryMapper
import com.planner.mapper.UserMapper
import com.planner.mapper.toGetCategoryResponseDto
import com.planner.service.CategoryService
import com.planner.service.CreateUserService
import com.planner.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userCreateService: CreateUserService,
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val categoryService: CategoryService,
    private val categoryMapper: CategoryMapper
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequestDto) =
        userCreateService.createUser(request)
            .let { userMapper.toCreateUserResponse(it) }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long) =
        userService.getUserById(userId)
            .let { userMapper.toGetUserResponse(it) }

    @GetMapping("/{userId}/categories")
    fun getUserCategories(@PathVariable userId: Long) =
        categoryService.getUserCategories(userId)
            .let { categoryMapper.toGetCategoryResponseDto(it) }
}
