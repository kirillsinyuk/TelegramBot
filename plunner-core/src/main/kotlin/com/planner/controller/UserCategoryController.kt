package com.planner.controller

import com.planner.mapper.CategoryMapper
import com.planner.mapper.toGetCategoryResponseDto
import com.planner.service.CategoryService
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
