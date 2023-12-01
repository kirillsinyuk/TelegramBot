package com.kvsinyuk.core.adapter.out.kafka

import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class CoreEventProducerImpl(
    private val kafkaAdapter: KafkaAdapter
) : CoreEventProducer {

    override fun produce(event: TelegramAdapterDataEvent) {
        val message = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.KEY, getKey(event.requestData.chatId))
            .build()
        kafkaAdapter.send(OutBinding.CORE_DATA_EVENT, message)
    }

    private fun getKey(chatId: Long) = "key-$chatId"
}
