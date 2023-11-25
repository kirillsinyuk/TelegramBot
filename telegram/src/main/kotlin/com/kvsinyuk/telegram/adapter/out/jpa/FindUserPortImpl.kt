package com.kvsinyuk.telegram.adapter.out.jpa

import com.kvsinyuk.telegram.adapter.mapper.TelegramUserEntityMapper
import com.kvsinyuk.telegram.application.port.out.FindUserPort
import org.springframework.stereotype.Component

@Component
class FindUserPortImpl(
    private val telegramUserRepository: TelegramUserRepository,
    private val entityMapper: TelegramUserEntityMapper
) : FindUserPort {
    override fun findByUserIdAndChatId(userId: Long, chatId: Long) =
        telegramUserRepository.findTelegramUserByUserIdAndChatId(userId, chatId)
            ?.let { entityMapper.fromEntity(it) }

    override fun existsByChatIdAndUserId(userId: Long, chatId: Long) =
        telegramUserRepository.existsByUserIdAndChatId(userId, chatId)
}
