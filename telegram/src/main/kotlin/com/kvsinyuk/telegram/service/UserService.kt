package com.kvsinyuk.telegram.service

import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.mapper.UserMapper
import com.kvsinyuk.telegram.repository.TelegramUserRepository
import com.kvsinyuk.telegram.service.streams.out.TelegramAdapterProducer
import com.kvsinyuk.plannercoreapi.model.kafka.CommandType.CREATE_USER
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
        if (!telegramUserRepository.existsByChatIdAndUserId(message.getChatId(), message.getUserId())) {
            telegramUserRepository.save(userMapper.messageToUser(message))
            sendUserCreateCmd(message)
        }
    }

    fun setUserId(id: Long, chatId: Long, userId: Long) {
        telegramUserRepository.setCoreUserId(userId, chatId, id)
    }

    private fun sendUserCreateCmd(message: Message) {
        val cmd = TelegramAdapterDataCmd(
            requestData = userMapper.messageToRequestData(message),
            createUserCmd = userMapper.messageToCreateUserCmd(message)
        )
        telegramAdapterProducer.produce(cmd, CREATE_USER)
    }

}