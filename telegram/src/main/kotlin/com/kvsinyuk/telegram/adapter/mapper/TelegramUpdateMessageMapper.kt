package com.kvsinyuk.telegram.adapter.mapper

import com.kvsinyuk.telegram.config.MapstructConfig
import com.kvsinyuk.telegram.domain.TelegramUpdateMessage
import com.pengrad.telegrambot.model.Update
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapstructConfig::class)
abstract class TelegramUpdateMessageMapper {

    @Mapping(target = "userId", expression = "java(update.message().from().id())")
    @Mapping(target = "chatId", expression = "java(update.message().chat().id())")
    @Mapping(target = "message", expression = "java(update.message().text())")
    abstract fun toMessage(update: Update): TelegramUpdateMessage
}
