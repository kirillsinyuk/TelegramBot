package com.kvsinyuk.telegram.application.service

import com.kvsinyuk.telegram.adapter.out.jpa.TelegramUserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val telegramUserRepository: TelegramUserRepository,
) {

    fun setUserId(id: Long, chatId: Long, userId: Long) {
        telegramUserRepository.setCoreUserId(userId, chatId, id)
    }
}