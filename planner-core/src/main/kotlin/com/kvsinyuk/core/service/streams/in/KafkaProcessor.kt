package com.kvsinyuk.core.service.streams.`in`

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import org.springframework.messaging.Message

interface KafkaProcessor {

    fun process(event: TelegramAdapterDataCmd)

    fun canApply(message: Message<TelegramAdapterDataCmd>): Boolean =
        CommandType.valueOf(message.headers["type"].toString()) == getType()

    fun getType(): CommandType
}
