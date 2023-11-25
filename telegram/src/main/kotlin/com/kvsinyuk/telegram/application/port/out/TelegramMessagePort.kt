package com.kvsinyuk.telegram.application.port.out

import com.pengrad.telegrambot.model.request.Keyboard

interface TelegramMessagePort {
    fun sendMessage(chatId: Long, msg: String, keyboard: Keyboard? = null);
}
