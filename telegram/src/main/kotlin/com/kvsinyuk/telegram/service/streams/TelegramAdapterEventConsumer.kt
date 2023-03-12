package com.kvsinyuk.telegram.service.streams

import com.kvsinyuk.telegram.model.TelegramUser

interface TelegramAdapterEventConsumer {

    fun consume(event: TelegramUser)
}