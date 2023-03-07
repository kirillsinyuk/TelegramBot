package com.planner.dto

import java.io.File
import java.math.BigDecimal


data class StatisticDto(
    val message: String,
    val totalSpend: BigDecimal,
    val statisticFile: File,
    val categotySpendings: List<StatisticDataDto>
)
