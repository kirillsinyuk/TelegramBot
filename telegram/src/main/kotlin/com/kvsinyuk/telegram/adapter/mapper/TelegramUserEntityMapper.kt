package com.kvsinyuk.telegram.adapter.mapper

import com.kvsinyuk.telegram.adapter.out.jpa.model.TelegramUserEntity
import com.kvsinyuk.telegram.config.MapstructConfig
import com.kvsinyuk.telegram.domain.TelegramUser
import org.mapstruct.Mapper

@Mapper(config = MapstructConfig::class)
abstract class TelegramUserEntityMapper {

    fun toEntity(userId: Long, chatId: Long): TelegramUserEntity {
        return TelegramUserEntity()
            .apply {
                this.userId = userId
                this.chatId = chatId
            }
    }

    abstract fun fromEntity(entity: TelegramUserEntity): TelegramUser
}
