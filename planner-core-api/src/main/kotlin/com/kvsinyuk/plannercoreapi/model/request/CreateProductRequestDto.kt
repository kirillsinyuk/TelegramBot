package com.planner.dto.request

import java.math.BigDecimal

data class CreateProductRequestDto(
    val categoryId: Long,
    val userId: Long,
    val price: BigDecimal,
    val description: String = ""
)
