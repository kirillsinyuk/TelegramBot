package com.kvsinyuk.telegram.adapter.mapper

import com.kvsinyuk.telegram.adapter.out.jpa.model.TelegramUserEntity
import com.kvsinyuk.telegram.config.MapstructConfig
import com.kvsinyuk.telegram.domain.TelegramUser
import org.mapstruct.Mapper

@Mapper(config = MapstructConfig::class)
abstract class TelegramUserEntityMapper {

    abstract fun toEntity(user: TelegramUser): TelegramUserEntity

    abstract fun fromEntity(entity: TelegramUserEntity): TelegramUser
}
