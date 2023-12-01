package com.kvsinyuk.core.adapter.out.kafka

import mu.KLogging
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class KafkaAdapter(
    private val streamBridge: StreamBridge
) {

    fun send(binding: OutBinding, data: Any) {
        streamBridge.send(binding.bindingName, data)
            .also { logResult(it, binding, data) }
    }

    fun send(binding: OutBinding, data: Any, headers: Map<String, String>) {
        streamBridge.send(binding.bindingName, data)
            .also { logResult(it, binding, data) }
    }

    private fun logResult(result: Boolean, binding: OutBinding, data: Any) {
        if (result) {
            logger.debug("Successfully sent message to $binding: $data")
        } else {
            logger.warn("Has a problem during sending a message to $binding: $data")
        }
    }

    companion object : KLogging()
}