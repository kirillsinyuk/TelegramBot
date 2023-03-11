package com.planner.service

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import jakarta.annotation.PostConstruct
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class BotUpdatesListener(
    private val bot: TelegramBot,
    private val commandSelectorService: CommandSelectorService
): UpdatesListener {

    @PostConstruct
    fun init() {
        bot.setUpdatesListener(this)
    }

    override fun process(updates: MutableList<Update>?): Int {
        updates?.forEach {
            logger.info("Processing update $it")
            commandSelectorService.processMessage(it.message())
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL
    }

    companion object: KLogging()
}