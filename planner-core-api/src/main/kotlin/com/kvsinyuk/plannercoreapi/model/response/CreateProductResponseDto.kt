package com.kvsinyuk.plannercoreapi.model.response

import java.math.BigDecimal
import java.util.UUID

data class CreateProductResponseDto(
    val id: UUID,
    val price: BigDecimal,
    val description: String? = null
)
