package com.kvsinyuk.core.adapter.`in`.http

import com.kvsinyuk.core.adapter.mapper.CategoryMapper
import com.kvsinyuk.core.application.service.CategoryService
import com.kvsinyuk.core.application.service.CreateCategoryService
import com.kvsinyuk.v1.http.CategoryApiProto.CreateCategoryRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService,
    private val createCategoryService: CreateCategoryService,
    private val categoryMapper: CategoryMapper
) {

    @PostMapping
    fun createCategory(@RequestBody request: CreateCategoryRequest) =
        createCategoryService.createCategory(request)
            .let { categoryMapper.toCategoryCreateResponse(it) }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: UUID) {
        categoryService.deleteCategoryById(id)
    }
}
