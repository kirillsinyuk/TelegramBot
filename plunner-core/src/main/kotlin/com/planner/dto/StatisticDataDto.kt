package com.planner.dto

import com.planner.model.Category
import java.math.BigDecimal


data class StatisticDataDto(
    val category: Category,
    val price: BigDecimal
) {
    override fun toString() = category.name + " - " + price
}
