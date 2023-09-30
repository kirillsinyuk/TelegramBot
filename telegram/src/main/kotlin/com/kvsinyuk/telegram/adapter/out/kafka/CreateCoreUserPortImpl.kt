package com.kvsinyuk.telegram.adapter.out.kafka

import com.kvsinyuk.telegram.domain.command.CreateCoreUserCmd
import org.springframework.stereotype.Component

@Component
class CreateCoreUserPortImpl(
    private val kafkaAdapter: KafkaAdapter
) {

    fun createUser(cmd: CreateCoreUserCmd) {
        kafkaAdapter.send(OutBinding.TELEGRAM_DATA_CMD, cmd)
    }
}