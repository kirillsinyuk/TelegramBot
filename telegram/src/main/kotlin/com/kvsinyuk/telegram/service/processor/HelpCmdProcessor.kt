package com.kvsinyuk.telegram.service.processor

import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.service.MessageService
import com.kvsinyuk.telegram.utils.Commands.HELP_CMD
import org.springframework.stereotype.Service

@Service
class HelpCmdProcessor(
    private val messageService: MessageService
): Processor {
    override fun process(message: Message) {
        messageService.sendMessage(message.chat().id(), "Hello! You used /help command!")
    }

    override fun canApply(message: Message) =
        message.text() == HELP_CMD
}