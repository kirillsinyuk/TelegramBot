package com.kvsinyuk.telegram.adapter.out.telegram

import com.kvsinyuk.telegram.application.port.out.TelegramMessagePort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.Keyboard
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Component

@Component
class TelegramMessageAdapter(
    private val bot: TelegramBot
) : TelegramMessagePort {

    override fun sendMessage(chatId: Long, msg: String, keyboard: Keyboard?) {
        getMessage(chatId, msg, keyboard)
            .let { bot.execute(it) }
    }

    private fun getMessage(chatId: Long, msg: String, keyboard: Keyboard? = null) =
        SendMessage(chatId, msg)
            .also { it.parseMode(ParseMode.HTML) }
            .also { keyboard?.let { kb -> it.replyMarkup(kb) } }
}
