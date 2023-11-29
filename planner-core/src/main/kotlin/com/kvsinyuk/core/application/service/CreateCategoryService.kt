package com.kvsinyuk.core.application.service

import com.kvsinyuk.core.adapter.out.jpa.entity.Category
import com.kvsinyuk.core.application.impl.GetUserPortImpl
import com.kvsinyuk.v1.http.CategoryApiProto
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateCategoryService(
    private val categoryService: CategoryService,
    private val getUserPortImpl: GetUserPortImpl
) {

    fun createCategory(request: CategoryApiProto.CreateCategoryRequest): Category {
        val user = getUserPortImpl.getUserById(UUID.fromString(request.userId))
        return categoryService.createCategory(user, request.name)
    }
}
