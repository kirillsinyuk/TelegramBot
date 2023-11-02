package com.kvsinyuk.telegram.application.port.`in`

import com.kvsinyuk.telegram.domain.TelegramUser

interface CreateTelegramUserUseCase {

    fun createUser(user: TelegramUser): TelegramUser
}
