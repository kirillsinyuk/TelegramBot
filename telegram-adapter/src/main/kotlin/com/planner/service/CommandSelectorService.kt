package com.planner.service

import com.pengrad.telegrambot.model.Message
import com.planner.service.processor.Processor
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