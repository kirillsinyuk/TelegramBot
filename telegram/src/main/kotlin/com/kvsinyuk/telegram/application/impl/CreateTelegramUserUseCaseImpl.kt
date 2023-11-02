package com.kvsinyuk.telegram.application.impl

import com.kvsinyuk.telegram.application.port.`in`.CreateTelegramUserUseCase
import com.kvsinyuk.telegram.application.port.out.CreateCoreUserPort
import com.kvsinyuk.telegram.application.port.out.SaveUserPort
import com.kvsinyuk.telegram.domain.TelegramUser
import com.kvsinyuk.telegram.domain.command.CreateCoreUserCmd
import org.springframework.stereotype.Component

@Component
class CreateTelegramUserUseCaseImpl(
    private val saveUserPort: SaveUserPort,
    private val createCoreUserPort: CreateCoreUserPort
): CreateTelegramUserUseCase {
    override fun createUser(user: TelegramUser): TelegramUser {
        return saveUserPort.saveUser(user)
            .also { createCoreUserPort.createUser(CreateCoreUserCmd()) }
    }
}
