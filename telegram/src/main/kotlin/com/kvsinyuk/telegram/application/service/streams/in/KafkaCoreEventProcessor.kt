package com.kvsinyuk.telegram.application.service.streams.`in`

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType
import com.kvsinyuk.plannercoreapi.model.kafka.event.CoreEvent
import org.springframework.messaging.Message

interface KafkaCoreEventProcessor {

    fun process(event: CoreEvent)

    fun canApply(message: Message<CoreEvent>): Boolean =
        CommandType.valueOf(message.headers["type"].toString()) == getType()

    fun getType(): CommandType
}
