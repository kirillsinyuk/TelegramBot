package com.kvsinyuk.telegram.service.streams

import com.kvsinyuk.plannercoreapi.model.kafka.TelegramAdapterDataCmd
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.function.Supplier

class TelegramCmdProducer: Supplier<Flux<Message<TelegramAdapterDataCmd>>>, TelegramAdapterProducer {

    val sink = Sinks.many().unicast().onBackpressureBuffer<Message<TelegramAdapterDataCmd>>()

    override fun produce(cmd: TelegramAdapterDataCmd) {
        val message = MessageBuilder
            .withPayload(cmd)
            .setHeader(KafkaHeaders.KEY, cmd.requestData.chatId)
            .build()
        sink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST)
    }

    override fun get(): Flux<Message<TelegramAdapterDataCmd>> = sink.asFlux()
}