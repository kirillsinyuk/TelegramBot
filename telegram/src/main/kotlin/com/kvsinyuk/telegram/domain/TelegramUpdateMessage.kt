package com.kvsinyuk.telegram.domain

data class TelegramUpdateMessage(
    val message: String,
    var userId: Long,
    var chatId: Long
)