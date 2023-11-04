package com.kvsinyuk.plannercoreapi.model.response

import java.util.UUID

data class GetCategoriesResponseDto(
    val categories: Set<CategoryResponseDto>
)

data class CategoryResponseDto(
    val id: UUID,
    val name: String
)
