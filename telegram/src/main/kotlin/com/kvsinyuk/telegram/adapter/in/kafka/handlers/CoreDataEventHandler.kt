package com.kvsinyuk.telegram.adapter.`in`.kafka.handlers

import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent

interface CoreDataEventHandler {
    fun process(event: TelegramAdapterDataEvent)

    fun canApply(event: TelegramAdapterDataEvent): Boolean
}