package com.kvsinyuk.telegram.adapter.`in`.telegram.handlers

import com.kvsinyuk.telegram.domain.TelegramUpdateMessage

interface TelegramUpdateHandler {
    fun process(update: TelegramUpdateMessage)

    fun canApply(update: TelegramUpdateMessage): Boolean
}
