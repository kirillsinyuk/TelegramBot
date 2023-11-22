package com.kvsinyuk.core.config

import com.kvsinyuk.core.service.streams.`in`.KafkaProcessor
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StreamBindingConfig(
    private val processors: List<KafkaProcessor>
) {

    @Bean
    fun telegramDataCmd() = { event: TelegramAdapterDataCmd ->
        processors.find { it.canApply(event) }
            ?.process(event)
    }
}
