package com.kvsinyuk.core.controller

import com.kvsinyuk.core.mapper.CategoryMapper
import com.kvsinyuk.core.mapper.toGetCategoryResponseDto
import com.kvsinyuk.core.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserCategoryController(
    private val categoryService: CategoryService,
    private val categoryMapper: CategoryMapper
) {

    @GetMapping("/{userId}/categories")
    fun getUserCategories(@PathVariable userId: Long) =
        categoryService.getUserCategories(userId)
            .let { categoryMapper.toGetCategoryResponseDto(it) }
}
