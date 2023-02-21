package com.planner.dto.response

data class GetCategoriesResponseDto(
    val categories: Set<CategoryResponseDto>
)

data class CategoryResponseDto(
    val id: Long,
    val name: String
)
