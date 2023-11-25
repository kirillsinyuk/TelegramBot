package com.kvsinyuk.telegram.application.port.out

import com.kvsinyuk.telegram.domain.TelegramUser

interface KafkaCoreCommandPort {
    fun createUser(user: TelegramUser)
}
