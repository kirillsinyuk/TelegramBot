package com.planner.dto.response

import java.math.BigDecimal

data class GetProductsResponseDto(
    val products: List<ProductResponseDto>
)

data class ProductResponseDto(
    val id: Long,
    val price: BigDecimal,
    val description: String
)
