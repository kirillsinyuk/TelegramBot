package com.kvsinyuk.core.service

import com.kvsinyuk.core.model.Category
import com.kvsinyuk.plannercoreapi.model.request.CreateCategoryRequestDto
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
