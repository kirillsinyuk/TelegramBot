package com.kvsinyuk.telegram.service.streams.out

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class TelegramCmdProducer(
    private val streamBridge: StreamBridge
) : TelegramAdapterProducer {

    override fun produce(cmd: TelegramAdapterDataCmd, type: CommandType) {
        val message = MessageBuilder
            .withPayload(cmd)
            .setHeader(KafkaHeaders.KEY, getKey(cmd.requestData.chatId))
            .setHeader("type", type.toString())
            .build()
        streamBridge.send("telegramDataCmd-out-0", message)
    }

    private fun getKey(chatId: Long) = "key-$chatId"
}