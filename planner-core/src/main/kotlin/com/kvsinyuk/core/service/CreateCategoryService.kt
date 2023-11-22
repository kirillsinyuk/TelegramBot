package com.kvsinyuk.core.service

import com.kvsinyuk.core.model.Category
import com.kvsinyuk.v1.http.CategoryApiProto
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateCategoryService(
    private val categoryService: CategoryService,
    private val userService: UserService
) {

    fun createCategory(request: CategoryApiProto.CreateCategoryRequest): Category {
        val user = userService.getUserById(UUID.fromString(request.userId))
        return categoryService.createCategory(user, request.name)
    }
}
