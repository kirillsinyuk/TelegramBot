package com.kvsinyuk.telegram.config

import com.kvsinyuk.plannercoreapi.model.kafka.event.CoreEvent
import com.kvsinyuk.telegram.service.streams.`in`.KafkaCoreEventProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import java.util.function.Consumer

@Configuration
class StreamBindingConfig(
    private val processors: List<KafkaCoreEventProcessor>
) {

    @Bean
    fun coreDataMessage() = Consumer { event: Message<CoreEvent> ->
        processors
            .find { it.canApply(event) }
            ?.process(event.payload)
    }
}
