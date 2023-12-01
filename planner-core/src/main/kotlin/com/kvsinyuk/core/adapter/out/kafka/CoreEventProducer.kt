package com.kvsinyuk.core.adapter.out.kafka

import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent

interface CoreEventProducer {

    fun produce(event: TelegramAdapterDataEvent)
}
