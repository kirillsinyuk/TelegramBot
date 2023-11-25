package com.kvsinyuk.telegram.adapter.out.kafka

import com.kvsinyuk.telegram.adapter.mapper.KafkaCommandMapper
import com.kvsinyuk.telegram.application.port.out.KafkaCoreCommandPort
import com.kvsinyuk.telegram.domain.TelegramUser
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd.CreateUser
import org.springframework.stereotype.Component

@Component
class KafkaCoreCommandAdapter(
    private val kafkaAdapter: KafkaAdapter,
    private val kafkaMapper: KafkaCommandMapper
) : KafkaCoreCommandPort {

    override fun createUser(user: TelegramUser) {
        val message = TelegramAdapterDataCmdProto.TelegramAdapterDataCmd.newBuilder()
            .setRequestData(kafkaMapper.toRequestData(user))
            .setCreateUser(CreateUser.newBuilder().build())
            .build()
        kafkaAdapter.send(OutBinding.TELEGRAM_DATA_CMD, message)
    }
}