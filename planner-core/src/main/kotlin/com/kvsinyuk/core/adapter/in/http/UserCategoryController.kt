package com.kvsinyuk.core.adapter.`in`.http

import com.kvsinyuk.core.adapter.mapper.CategoryMapper
import com.kvsinyuk.core.application.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserCategoryController(
    private val categoryService: CategoryService,
    private val categoryMapper: CategoryMapper
) {

    @GetMapping("/{userId}/categories")
    fun getUserCategories(@PathVariable userId: UUID) =
        categoryService.getUserCategories(userId)
            .let { categoryMapper.toGetCategoryResponse(it) }
}
