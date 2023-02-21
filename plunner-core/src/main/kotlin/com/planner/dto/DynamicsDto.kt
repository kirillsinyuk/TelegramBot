package com.planner.dto

import java.io.File
import java.math.BigDecimal

data class DynamicsDto(
    val message: String,
    val totalSpend: BigDecimal,
    val dynamicsFile: File,
    val monthSpendings: Map<String, List<DynamicsDataDto>> = emptyMap()
)
