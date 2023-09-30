package com.kvsinyuk.telegram.repository

import com.kvsinyuk.telegram.adapter.out.jpa.model.TelegramUser
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface TelegramUserRepository: CrudRepository<TelegramUser, Long> {

    fun findTelegramUserByUserIdAndChatId(userId: Long, chatId: Long): TelegramUser?

    fun existsByChatIdAndUserId(chatId: Long, userId: Long): Boolean

    @Modifying
    @Query(
        "update TelegramUser user set user.coreUserId=:coreUserId " +
                "where user.chatId=:chatId and user.userId=:userId"
    )
    fun setCoreUserId(coreUserId: Long, chatId: Long, userId: Long): Int

}