package com.planner.service

import com.planner.dto.request.CreateCategoryRequestDto
import com.planner.model.Category
import org.springframework.stereotype.Service

@Service
class CreateCategoryService(
    private val categoryService: CategoryService,
    private val userService: UserService
) {

    fun createCategory(request: CreateCategoryRequestDto): Category {
        val user = userService.getUserById(request.userId)
        return categoryService.createCategory(user, request.name)
    }
}
