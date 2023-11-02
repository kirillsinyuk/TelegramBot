package com.kvsinyuk.telegram.adapter.out.jpa

import com.kvsinyuk.telegram.adapter.mapper.TelegramUserEntityMapper
import com.kvsinyuk.telegram.application.port.out.FindUserPort
import com.kvsinyuk.telegram.application.port.out.SaveUserPort
import com.kvsinyuk.telegram.domain.TelegramUser
import org.springframework.stereotype.Component

@Component
class UserPortImpl(
    private val telegramUserRepository: TelegramUserRepository,
    private val entityMapper: TelegramUserEntityMapper
) : FindUserPort, SaveUserPort {
    override fun findByUserIdAndChatId(userId: Long, chatId: Long) =
        telegramUserRepository.findTelegramUserByUserIdAndChatId(userId, chatId)
            ?.let { entityMapper.fromEntity(it) }

    override fun saveUser(user: TelegramUser) =
        entityMapper.toEntity(user)
            .let { telegramUserRepository.save(it) }
            .let { entityMapper.fromEntity(it) }

    override fun existsByChatIdAndUserId(userId: Long, chatId: Long) =
        telegramUserRepository.existsByUserIdAndChatId(userId, chatId)
}
