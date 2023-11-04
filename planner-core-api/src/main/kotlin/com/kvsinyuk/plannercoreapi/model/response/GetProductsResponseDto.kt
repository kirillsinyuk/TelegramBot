package com.kvsinyuk.plannercoreapi.model.response

import java.math.BigDecimal
import java.util.UUID

data class GetProductsResponseDto(
    val products: List<ProductResponseDto>
)

data class ProductResponseDto(
    val id: UUID,
    val price: BigDecimal,
    val description: String
)
