package com.kvsinyuk.telegram.adapter.`in`.telegram

import com.kvsinyuk.telegram.adapter.mapper.TelegramUpdateMessageMapper
import com.kvsinyuk.telegram.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import jakarta.annotation.PostConstruct
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class BotUpdatesListener(
    private val bot: TelegramBot,
    private val telegramUpdateHandlers: List<TelegramUpdateHandler>,
    private val telegramUpdateMessageMapper: TelegramUpdateMessageMapper
) : UpdatesListener {

    @PostConstruct
    fun init() {
        bot.setUpdatesListener(this)
    }

    override fun process(updates: MutableList<Update>?): Int {
        updates?.forEach {
            val update = telegramUpdateMessageMapper.toMessage(it)
            logger.debug("Processing update $update")
            telegramUpdateHandlers.filter { it.canApply(update) }
                .forEach { it.process(update) }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL
    }

    companion object : KLogging()
}