package com.kvsinyuk.telegram.application.impl

import com.kvsinyuk.telegram.application.port.`in`.CreateTelegramUserUseCase
import com.kvsinyuk.telegram.application.port.`in`.CreateTelegramUserUseCase.CreateUserCommand
import com.kvsinyuk.telegram.application.port.out.KafkaCoreCommandPort
import com.kvsinyuk.telegram.application.port.out.SaveUserPort
import com.kvsinyuk.telegram.domain.TelegramUser
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateTelegramUserUseCaseImpl(
    private val saveUserPort: SaveUserPort,
    private val createCoreUserPort: KafkaCoreCommandPort
) : CreateTelegramUserUseCase {

    @Transactional
    override fun createUser(command: CreateUserCommand): TelegramUser {
        return saveUserPort.saveUser(command.userId, command.chatId)
            .also { createCoreUserPort.createUser(it) }
    }
}
