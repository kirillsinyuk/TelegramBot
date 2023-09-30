package com.kvsinyuk.telegram.adapter.out.jpa

import com.kvsinyuk.telegram.adapter.out.jpa.model.TelegramUserEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface TelegramUserRepository: CrudRepository<TelegramUserEntity, Long> {

    fun findTelegramUserByUserIdAndChatId(userId: Long, chatId: Long): TelegramUserEntity?

    fun existsByUserIdAndChatId(userId: Long, chatId: Long): Boolean

    @Modifying
    @Query(
        "update TelegramUserEntity as user set user.coreUserId=:coreUserId "
                + "where user.chatId=:chatId and user.userId=:userId"
    )
    fun setCoreUserId(coreUserId: Long, chatId: Long, userId: Long): Int
}
