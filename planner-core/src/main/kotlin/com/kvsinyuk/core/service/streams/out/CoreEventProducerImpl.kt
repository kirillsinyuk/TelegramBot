package com.kvsinyuk.core.service.streams.out

import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class CoreEventProducerImpl(
    private val streamBridge: StreamBridge
) : CoreEventProducer {

    override fun produce(event: TelegramAdapterDataEvent) {
        val message = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.KEY, getKey(event.requestData.chatId))
            .build()
        streamBridge.send("core-data-message-out-0", message)
    }

    private fun getKey(chatId: Long) = "key-$chatId"
}
