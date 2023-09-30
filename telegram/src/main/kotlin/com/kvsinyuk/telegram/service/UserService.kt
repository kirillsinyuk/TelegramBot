package com.kvsinyuk.telegram.service

import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.mapper.UserMapper
import com.kvsinyuk.telegram.repository.TelegramUserRepository
import com.kvsinyuk.telegram.adapter.out.kafka.KafkaAdapter
import com.kvsinyuk.telegram.adapter.out.kafka.OutBinding.TELEGRAM_DATA_CMD
import com.kvsinyuk.telegram.utils.getChatId
import com.kvsinyuk.telegram.utils.getUserId
import org.springframework.stereotype.Service

@Service
class UserService(
    private val kafkaAdapter: KafkaAdapter,
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
        kafkaAdapter.send(TELEGRAM_DATA_CMD, cmd)
    }

}