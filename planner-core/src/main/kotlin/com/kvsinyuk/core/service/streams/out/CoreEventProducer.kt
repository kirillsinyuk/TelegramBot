package com.kvsinyuk.core.service.streams.out

import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent

interface CoreEventProducer {

    fun produce(event: TelegramAdapterDataEvent)
}
