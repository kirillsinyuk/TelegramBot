package com.planner.dto

import org.jfree.data.time.Month
import java.math.BigDecimal

data class DynamicsDataDto(
    val month: Month,
    val spend: BigDecimal,
    val userName: String
) {
    override fun toString() = month.toString() + " - " + spend
}
