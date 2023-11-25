package com.kvsinyuk.telegram.adapter.`in`.kafka

import com.kvsinyuk.telegram.adapter.`in`.kafka.handlers.CoreDataEventHandler
import com.kvsinyuk.v1.kafka.TelegramAdapterDataEventProto.TelegramAdapterDataEvent
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class CoreDataEventAdapter(
    private val handlers: List<CoreDataEventHandler>
) {

    @Bean
    fun coreDataEvent() = Consumer { event: TelegramAdapterDataEvent ->
        handlers.find { it.canApply(event) }
            ?.process(event)
    }
}
