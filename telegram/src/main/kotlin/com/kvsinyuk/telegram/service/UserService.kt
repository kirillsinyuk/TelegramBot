package com.kvsinyuk.telegram.service

import com.kvsinyuk.plannercoreapi.model.kafka.TelegramAdapterDataCmd
import com.kvsinyuk.plannercoreapi.model.kafka.RequestData
import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.mapper.UserMapper
import com.kvsinyuk.telegram.repository.TelegramUserRepository
import com.kvsinyuk.telegram.service.streams.TelegramAdapterProducer
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.CommandType
import com.kvsinyuk.telegram.utils.getChatId
import com.kvsinyuk.telegram.utils.getUserId
import org.springframework.stereotype.Service

@Service
class UserService(
    private val telegramAdapterProducer: TelegramAdapterProducer,
    private val telegramUserRepository: TelegramUserRepository,
    private val userMapper: UserMapper
) {

    fun createUserIfNotExist(message: Message) {
        if (telegramUserRepository.existsByChatIdAndUserId(message.getChatId(), message.getUserId())) {
            telegramUserRepository.save(userMapper.messageToUser(message))
            val cmd = TelegramAdapterDataCmd(
                type = CommandType.CREATE_USER,
                requestData = RequestData(message.chat().id(), message.from().id()),
                createUserCmd = userMapper.messageToCreateUserCmd(message)
            )
            telegramAdapterProducer.produce(cmd)
        }
    }

}