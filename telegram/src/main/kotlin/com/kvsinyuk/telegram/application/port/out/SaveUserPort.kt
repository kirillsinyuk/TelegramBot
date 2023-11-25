package com.kvsinyuk.telegram.application.port.out

import com.kvsinyuk.telegram.domain.TelegramUser

interface SaveUserPort {
    fun saveUser(userId: Long, chatId: Long): TelegramUser
    fun updateUserCoreId(userId: Long, chatId: Long, userCoreId: String): TelegramUser
}