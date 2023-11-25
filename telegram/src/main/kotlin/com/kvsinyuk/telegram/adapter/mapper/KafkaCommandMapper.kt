package com.kvsinyuk.telegram.adapter.mapper

import com.kvsinyuk.telegram.config.MapstructConfig
import com.kvsinyuk.telegram.domain.TelegramUser
import com.kvsinyuk.v1.kafka.RequestDataOuterClass
import org.mapstruct.Mapper

@Mapper(config = MapstructConfig::class)
abstract class KafkaCommandMapper {

    fun toRequestData(user: TelegramUser): RequestDataOuterClass.RequestData {
        return RequestDataOuterClass.RequestData.newBuilder()
            .setUserId(user.userId)
            .setChatId(user.chatId)
            .build()
    }
}
