package com.kvsinyuk.telegram.service.streams

import com.kvsinyuk.telegram.model.TelegramUser
import java.util.function.Consumer

class CoreDataConsumer(
    private val telegramAdapterEventConsumer: TelegramAdapterEventConsumer
): Consumer<TelegramUser> {

    override fun accept(event: TelegramUser) {
        telegramAdapterEventConsumer.consume(event)
    }
}