package com.planner.dto.response

data class GetProductsResponseDto(
    val products: List<ProductResponseDto>
)

data class ProductResponseDto(
    val id: Long,
    val price: Float,
    val description: String
)
