package com.kvsinyuk.telegram.application.port.`in`

import com.kvsinyuk.telegram.domain.TelegramUser

interface CreateTelegramUserUseCase {

    fun createUser(command: CreateUserCommand): TelegramUser

    data class CreateUserCommand(
        var userId: Long,
        var chatId: Long
    )
}
