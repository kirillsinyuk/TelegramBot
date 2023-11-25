package com.kvsinyuk.core.kafka

import com.kvsinyuk.core.service.streams.`in`.TelegramCommandHandler
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class TelegramDataCmdAdapter(
    private val handlers: List<TelegramCommandHandler>
) {

    @Bean
    fun telegramDataCmd() = Consumer { event: TelegramAdapterDataCmd ->
        handlers.find { it.canApply(event) }
            ?.process(event)
    }
}
