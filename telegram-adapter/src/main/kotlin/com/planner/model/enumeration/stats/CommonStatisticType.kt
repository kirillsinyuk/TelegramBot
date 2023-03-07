package com.planner.model.enumeration.stats

import com.planner.model.enumeration.DataAction
import java.util.*

enum class CommonStatisticType(var ruName: String, val label: String){
    CATEGORY("По категориям", "cat"),
    DYNAMICS("Динамика", "dyn");

    fun getByRuName(ruName: String): DataAction? {
        return Arrays.stream(DataAction.values())
            .filter { dataAction: DataAction? ->
                dataAction!!.ruName.lowercase(Locale.getDefault()) == ruName.lowercase(
                    Locale.getDefault()
                )
            }
            .findFirst()
            .orElse(null)
    }

    fun getByName(name: String): DataAction? {
        return Arrays.stream(DataAction.values())
            .filter { dataAction: DataAction? ->
                dataAction!!.ruName.lowercase(Locale.getDefault()) == name.lowercase(
                    Locale.getDefault()
                )
            }
            .findFirst()
            .orElse(null)
    }
}