package com.kvsinyuk.core.adapter.mapper

import com.kvsinyuk.core.config.MapperConfiguration
import com.kvsinyuk.core.adapter.out.jpa.entity.User
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd
import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent
import org.mapstruct.Mapper

@Mapper(
    uses = [RequestDataMapper::class],
    config = MapperConfiguration::class
)
abstract class CoreEventMapper {

    abstract fun toUserCreatedProto(user: User): TelegramAdapterDataEvent.UserCreated

    fun toUserCreatedEventProto(event: TelegramAdapterDataCmd, user: User): TelegramAdapterDataEvent =
        TelegramAdapterDataEvent.newBuilder()
            .setRequestData(event.requestData)
            .setUserCreated(toUserCreatedProto(user))
            .build()
}
