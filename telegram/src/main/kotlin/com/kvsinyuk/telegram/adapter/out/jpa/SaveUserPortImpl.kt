package com.kvsinyuk.telegram.adapter.out.jpa

import com.kvsinyuk.telegram.adapter.mapper.TelegramUserEntityMapper
import com.kvsinyuk.telegram.application.port.out.SaveUserPort
import org.springframework.stereotype.Component

@Component
class SaveUserPortImpl(
    private val telegramUserRepository: TelegramUserRepository,
    private val entityMapper: TelegramUserEntityMapper
) : SaveUserPort {

    override fun saveUser(userId: Long, chatId: Long) =
        entityMapper.toEntity(userId, chatId)
            .let { telegramUserRepository.save(it) }
            .let { entityMapper.fromEntity(it) }

    override fun updateUserCoreId(userId: Long, chatId: Long, userCoreId: String) =
        entityMapper.toEntity(userId, chatId)
            .let { telegramUserRepository.save(it) }
            .let { entityMapper.fromEntity(it) }
}
