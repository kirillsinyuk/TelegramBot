package com.kvsinyuk.plannercoreapi.model.request

import java.math.BigDecimal
import java.util.UUID

data class CreateProductRequestDto(
    val categoryId: UUID,
    val userId: UUID,
    val price: BigDecimal,
    val description: String = ""
)
