package com.kvsinyuk.telegram.service.processor

import com.pengrad.telegrambot.model.Message

interface Processor {

    fun process(message: Message)

    fun canApply(message: Message): Boolean
}
