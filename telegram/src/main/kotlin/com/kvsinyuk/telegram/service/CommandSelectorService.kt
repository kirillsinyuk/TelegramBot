package com.kvsinyuk.telegram.service

import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.service.processor.Processor
import org.springframework.stereotype.Service

@Service
class CommandSelectorService(
    private val processors: List<Processor>
) {

    fun processMessage(message: Message) {
        processors
            .firstOrNull { it.canApply(message) }
            ?.process(message)
    }
}