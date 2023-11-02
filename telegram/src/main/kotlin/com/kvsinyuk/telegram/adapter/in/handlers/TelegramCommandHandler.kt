package com.kvsinyuk.telegram.adapter.`in`.handlers

import com.kvsinyuk.telegram.domain.TelegramUpdateMessage
import com.kvsinyuk.telegram.application.service.processor.TelegramProcessor
import org.springframework.stereotype.Service

@Service
class TelegramCommandHandler(
    private val telegramProcessors: List<TelegramProcessor>
) {

    fun processMessage(update: TelegramUpdateMessage) {
        telegramProcessors.filter { it.canApply(update) }
            .forEach { it.process(update) }
    }
}
