package com.kvsinyuk.telegram.application.service.processor

import com.kvsinyuk.telegram.domain.TelegramUpdateMessage
import com.kvsinyuk.telegram.application.service.MessageService
import com.kvsinyuk.telegram.utils.Commands.HELP_CMD
import org.springframework.stereotype.Service

@Service
class HelpCmdProcessor(
    private val messageService: MessageService
): TelegramProcessor {
    override fun process(update: TelegramUpdateMessage) {
        messageService.sendMessage(update.chatId, "Hello! You used /help command!")
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean =
        update.message == HELP_CMD
}
