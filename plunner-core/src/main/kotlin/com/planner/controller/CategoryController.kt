package com.planner.controller

import com.planner.dto.request.CreateCategoryRequestDto
import com.planner.mapper.CategoryMapper
import com.planner.mapper.toGetCategoryResponseDto
import com.planner.service.CategoryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService,
    private val categoryMapper: CategoryMapper
) {

    @GetMapping("/byGroup/{groupId}")
    fun getGroupCategories(@PathVariable groupId: Long) =
        categoryService.getGroupCategories(groupId)
            .let { categoryMapper.toGetCategoryResponseDto(it) }

    @GetMapping("/byUser/{userId}")
    fun getUserCategories(@PathVariable userId: Long) =
        categoryService.getUserCategories(userId)
            .let { categoryMapper.toGetCategoryResponseDto(it) }

    @PostMapping
    fun createCategory(@RequestBody request: CreateCategoryRequestDto) =
        categoryService.createCategory(request)
            .let { categoryMapper.toCategoryCreateResponse(it) }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long) {
        categoryService.deleteCategoryById(id)
    }
}
