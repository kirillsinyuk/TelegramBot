package com.kvsinyuk.telegram.application.service.processor

import com.kvsinyuk.telegram.application.port.`in`.CreateTelegramUserUseCase
import com.kvsinyuk.telegram.application.port.out.FindUserPort
import com.kvsinyuk.telegram.domain.TelegramUpdateMessage
import com.kvsinyuk.telegram.domain.TelegramUser
import com.kvsinyuk.telegram.utils.Commands.START_CMD
import org.springframework.stereotype.Service

@Service
class StartCmdProcessor(
    private val findUserPort: FindUserPort,
    private val createTelegramUserUseCase: CreateTelegramUserUseCase
): TelegramProcessor {
    override fun process(update: TelegramUpdateMessage) {
        if (!findUserPort.existsByChatIdAndUserId(update.userId, update.chatId)) {
            val user = TelegramUser(userId = update.userId, chatId = update.chatId)
            createTelegramUserUseCase.createUser(user)
        }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.message == START_CMD
}
