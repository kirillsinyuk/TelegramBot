package com.kvsinyuk.telegram.adapter.`in`

import com.kvsinyuk.telegram.adapter.`in`.handlers.TelegramCommandHandler
import com.kvsinyuk.telegram.adapter.mapper.TelegramUpdateMessageMapper
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import jakarta.annotation.PostConstruct
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class BotUpdatesListener(
    private val bot: TelegramBot,
    private val telegramCommandHandler: TelegramCommandHandler,
    private val telegramUpdateMessageMapper: TelegramUpdateMessageMapper
): UpdatesListener {

    @PostConstruct
    fun init() {
        bot.setUpdatesListener(this)
    }

    override fun process(updates: MutableList<Update>?): Int {
        updates?.forEach {
            val update = telegramUpdateMessageMapper.toMessage(it)
            logger.debug("Processing update $update")
            telegramCommandHandler.processMessage(update)
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL
    }

    companion object: KLogging()
}