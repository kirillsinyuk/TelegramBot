package com.planner.dto.request

data class CreateProductRequestDto(
    val categoryId: Long,
    val price: Float,
    val description: String = ""
)
