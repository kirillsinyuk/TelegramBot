package com.kvsinyuk.core.service.streams.`in`

import com.kvsinyuk.core.mapper.CoreEventMapper
import com.kvsinyuk.core.mapper.UserMapper
import com.kvsinyuk.core.service.UserService
import com.kvsinyuk.core.service.streams.out.CoreEventProducer
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import com.kvsinyuk.plannercoreapi.model.kafka.CommandType.CREATE_USER
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserProcessor(
    private val userMapper: UserMapper,
    private val coreEventMapper: CoreEventMapper,
    private val userService: UserService,
    private val eventProducer: CoreEventProducer
): KafkaProcessor {

    @Transactional
    override fun process(event: TelegramAdapterDataCmd) {
        println("Received $event")

        val user = event.createUserCmd
            ?.let { userMapper.toUser(it) }
            ?.let { userService.createUser(it) }

        user?.let { coreEventMapper.toUserCreateEvent(event, it) }
            ?.also { eventProducer.produce(it,getType()) }

        println("Created user $user")
    }

    override fun getType() = CREATE_USER
}
