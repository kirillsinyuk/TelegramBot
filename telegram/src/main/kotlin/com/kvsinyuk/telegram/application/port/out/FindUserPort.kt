package com.kvsinyuk.telegram.application.port.out

import com.kvsinyuk.telegram.domain.TelegramUser

interface FindUserPort {
    fun findByUserIdAndChatId(userId: Long, chatId: Long): TelegramUser?

    fun existsByChatIdAndUserId(userId: Long, chatId: Long): Boolean
}