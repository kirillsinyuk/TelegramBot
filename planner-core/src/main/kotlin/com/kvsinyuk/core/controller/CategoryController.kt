package com.kvsinyuk.core.controller

import com.kvsinyuk.core.mapper.CategoryMapper
import com.kvsinyuk.core.service.CategoryService
import com.kvsinyuk.core.service.CreateCategoryService
import com.planner.dto.request.CreateCategoryRequestDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService,
    private val createCategoryService: CreateCategoryService,
    private val categoryMapper: CategoryMapper
) {

    @PostMapping
    fun createCategory(@RequestBody request: CreateCategoryRequestDto) =
        createCategoryService.createCategory(request)
            .let { categoryMapper.toCategoryCreateResponse(it) }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long) {
        categoryService.deleteCategoryById(id)
    }
}
