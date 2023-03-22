package com.kvsinyuk.telegram.service.streams.`in`

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType.CREATE_USER
import com.kvsinyuk.plannercoreapi.model.kafka.event.CoreEvent
import com.kvsinyuk.telegram.service.MessageService
import com.kvsinyuk.telegram.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserProcessor(
    private val messageService: MessageService,
    private val userService: UserService
): KafkaCoreEventProcessor {

    @Transactional
    override fun process(event: CoreEvent) {
        println("Received $event")
        event.createUserCmd?.id
            ?.also { userService.setUserId(event.requestData.userId, event.requestData.chatId, it) }

        messageService.sendMessage(event.requestData.chatId, "Hello! User with id${event.requestData.userId} was registered in bot.")
    }

    override fun getType() = CREATE_USER
}
