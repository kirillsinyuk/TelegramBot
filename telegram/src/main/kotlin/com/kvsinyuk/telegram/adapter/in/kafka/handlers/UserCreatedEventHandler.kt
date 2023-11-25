package com.kvsinyuk.telegram.adapter.`in`.kafka.handlers

import com.kvsinyuk.telegram.application.port.out.TelegramMessagePort
import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent
import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent.EventCase
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class UserCreatedEventHandler(
    private val telegramMessagePort: TelegramMessagePort
) : CoreDataEventHandler {

    override fun canApply(event: TelegramAdapterDataEvent) =
        event.eventCase == EventCase.USER_CREATED

    override fun process(event: TelegramAdapterDataEvent) {
        logger.info { "Received $event" }

        telegramMessagePort.sendMessage(event.requestData.chatId, "Hello! User with id${event.requestData.userId} was registered in bot.")
    }

    companion object: KLogging()
}
