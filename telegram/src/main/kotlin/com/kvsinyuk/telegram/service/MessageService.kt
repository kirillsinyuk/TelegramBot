package com.kvsinyuk.telegram.service

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.Keyboard
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val bot: TelegramBot
) {

    fun sendMessage(chatId: Long, msg: String, keyboard: Keyboard? = null) {
        getMessage(chatId, msg, keyboard)
            .let { bot.execute(it) }
    }

    private fun getMessage(chatId: Long, msg: String, keyboard: Keyboard? = null) =
        SendMessage(chatId, msg)
            .also { it.parseMode(ParseMode.HTML) }
            .also { keyboard?.let { kb -> it.replyMarkup(kb) } }
}