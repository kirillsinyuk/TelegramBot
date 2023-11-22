package com.kvsinyuk.core.service.streams.`in`

import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd

interface KafkaProcessor {

    fun process(event: TelegramAdapterDataCmd)

    fun canApply(event: TelegramAdapterDataCmd): Boolean
}
