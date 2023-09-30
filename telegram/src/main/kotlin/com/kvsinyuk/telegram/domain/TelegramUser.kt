package com.kvsinyuk.telegram.domain

data class TelegramUser(
    val id: Long? = null,
    var userId: Long,
    var chatId: Long,
    var coreUserId: Long? = null
)