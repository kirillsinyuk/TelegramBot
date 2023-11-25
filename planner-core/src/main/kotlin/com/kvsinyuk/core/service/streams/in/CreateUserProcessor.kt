package com.kvsinyuk.core.service.streams.`in`

import com.kvsinyuk.core.mapper.CoreEventMapper
import com.kvsinyuk.core.mapper.UserMapper
import com.kvsinyuk.core.service.UserService
import com.kvsinyuk.core.service.streams.out.CoreEventProducer
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserProcessor(
    private val userMapper: UserMapper,
    private val coreEventMapper: CoreEventMapper,
    private val userService: UserService,
    private val eventProducer: CoreEventProducer
): TelegramCommandHandler {

    override fun canApply(event: TelegramAdapterDataCmd) =
        event.hasCreateUser()

    @Transactional
    override fun process(event: TelegramAdapterDataCmd) {
        logger.info { "Received $event" }

        val user = event.createUser
            ?.let { userMapper.toUser(it) }
            ?.let { userService.createUser(it) }

        user?.let { coreEventMapper.toUserCreatedEventProto(event, it) }
            ?.also { eventProducer.produce(it) }

        logger.info {"Created user $user" }
    }

    companion object: KLogging()
}
