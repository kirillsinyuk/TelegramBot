package com.kvsinyuk.core.mapper

import com.google.type.Money
import com.kvsinyuk.core.config.MapperConfiguration
import org.mapstruct.Mapper
import java.math.BigDecimal

@Mapper(config = MapperConfiguration::class)
abstract class MoneyMapper {

    fun toMoneyProto(money: BigDecimal): Money =
        Money.newBuilder()
            .setCurrencyCode("RUB")
            .setUnits(money.longValueExact())
            .build()

    fun fromMoneyProto(money: Money) = BigDecimal.valueOf(money.units)
}
