package com.kvsinyuk.core.config

import com.kvsinyuk.core.service.streams.`in`.KafkaProcessor
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
class StreamBindingConfig(
    private val processors: List<KafkaProcessor>
) {

    @Bean
    fun telegramDataCmd() = Consumer { event: Message<TelegramAdapterDataCmd> ->
        processors
            .find { it.canApply(event) }
            ?.process(event.payload)
    }
}
