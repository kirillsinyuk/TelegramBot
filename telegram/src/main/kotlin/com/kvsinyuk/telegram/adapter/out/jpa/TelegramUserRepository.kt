package com.kvsinyuk.telegram.adapter.out.jpa

import com.kvsinyuk.telegram.adapter.out.jpa.model.TelegramUserEntity
import org.springframework.data.repository.CrudRepository

interface TelegramUserRepository: CrudRepository<TelegramUserEntity, Long> {

    fun findTelegramUserByUserIdAndChatId(userId: Long, chatId: Long): TelegramUserEntity?

    fun existsByUserIdAndChatId(userId: Long, chatId: Long): Boolean
}
