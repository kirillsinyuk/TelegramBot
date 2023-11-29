package com.kvsinyuk.core.adapter.`in`.kafka.handler

import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd

interface TelegramCommandHandler {

    fun process(event: TelegramAdapterDataCmd)

    fun canApply(event: TelegramAdapterDataCmd): Boolean
}
