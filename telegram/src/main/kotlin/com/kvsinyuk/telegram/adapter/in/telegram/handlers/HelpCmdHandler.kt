package com.kvsinyuk.telegram.adapter.`in`.telegram.handlers

import com.kvsinyuk.telegram.application.port.out.TelegramMessagePort
import com.kvsinyuk.telegram.domain.TelegramUpdateMessage
import com.kvsinyuk.telegram.utils.Commands.HELP_CMD
import org.springframework.stereotype.Service

@Service
class HelpCmdHandler(
    private val telegramMessagePort: TelegramMessagePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        telegramMessagePort.sendMessage(update.chatId, "Hello! You used /help command!")
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean =
        update.message == HELP_CMD
}
