package com.planner.dto.response

import java.math.BigDecimal

data class CreateProductResponseDto(
    val id: Long,
    val price: BigDecimal,
    val description: String? = null
)
