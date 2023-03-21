package com.kvsinyuk.core.service.streams.out

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType
import com.kvsinyuk.plannercoreapi.model.kafka.event.CoreEvent
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class CoreEventProducerImpl(
    private val streamBridge: StreamBridge
) : CoreEventProducer {

    override fun produce(cmd: CoreEvent, type: CommandType) {
        val message = MessageBuilder
            .withPayload(cmd)
            .setHeader(KafkaHeaders.KEY, getKey(cmd.requestData.chatId))
            .setHeader("type", type.toString())
            .build()
        streamBridge.send("core-data-message-out-0", message)
    }

    private fun getKey(chatId: Long) = "key-$chatId"
}
