package com.planner.dto.response

data class CreateProductResponseDto(
    val id: Long,
    val price: Float,
    val description: String? = null
)
