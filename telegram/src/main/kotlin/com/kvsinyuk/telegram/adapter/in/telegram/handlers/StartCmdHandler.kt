package com.kvsinyuk.telegram.adapter.`in`.telegram.handlers

import com.kvsinyuk.telegram.application.port.`in`.CreateTelegramUserUseCase
import com.kvsinyuk.telegram.application.port.`in`.CreateTelegramUserUseCase.CreateUserCommand
import com.kvsinyuk.telegram.application.port.out.FindUserPort
import com.kvsinyuk.telegram.domain.TelegramUpdateMessage
import com.kvsinyuk.telegram.utils.Commands.START_CMD
import org.springframework.stereotype.Service

@Service
class StartCmdHandler(
    private val findUserPort: FindUserPort,
    private val createTelegramUserUseCase: CreateTelegramUserUseCase
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        if (!findUserPort.existsByChatIdAndUserId(update.userId, update.chatId)) {
            createTelegramUserUseCase.createUser(CreateUserCommand(update.userId, update.chatId))
        }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.message == START_CMD
}
