package com.kvsinyuk.core.adapter.`in`.kafka.handler

import com.kvsinyuk.core.adapter.mapper.CoreEventMapper
import com.kvsinyuk.core.adapter.out.kafka.CoreEventProducer
import com.kvsinyuk.core.application.port.`in`.CreateUserPort
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserHandler(
    private val coreEventMapper: CoreEventMapper,
    private val createUserPort: CreateUserPort,
    private val eventProducer: CoreEventProducer
): TelegramCommandHandler {

    override fun canApply(event: TelegramAdapterDataCmd) =
        event.hasCreateUser()

    @Transactional
    override fun process(event: TelegramAdapterDataCmd) {
        logger.info { "Received $event" }

        val user = event.createUser
            ?.let { createUserPort.createUser(it) }

        user?.let { coreEventMapper.toUserCreatedEventProto(event, it) }
            ?.also { eventProducer.produce(it) }

        logger.info {"Created user $user" }
    }

    companion object: KLogging()
}
