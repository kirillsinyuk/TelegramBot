package com.kvsinyuk.plannercoreapi.model.request

import java.util.Date

data class GetProductsRequestDto(
    val from: Date?,
    val to: Date?
)
