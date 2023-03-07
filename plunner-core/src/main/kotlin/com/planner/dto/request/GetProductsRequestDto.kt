package com.planner.dto.request

import java.util.Date

data class GetProductsRequestDto(
    val userId: Long,
    val from: Date,
    val to: Date
)
