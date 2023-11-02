package com.kvsinyuk.telegram.application.service.processor

import com.kvsinyuk.telegram.domain.TelegramUpdateMessage

interface TelegramProcessor {

    fun process(update: TelegramUpdateMessage)

    fun canApply(update: TelegramUpdateMessage): Boolean
}
