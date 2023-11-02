package com.kvsinyuk.telegram.application.port.out

import com.kvsinyuk.telegram.domain.TelegramUser

interface SaveUserPort {
    fun saveUser(user: TelegramUser): TelegramUser
}