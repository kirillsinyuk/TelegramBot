package com.kvsinyuk.telegram.adapter.out.jpa.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "telegram_user")
class TelegramUserEntity {
    @Id
    var id: UUID = UUID.randomUUID()

    var userId: Long = 0

    var chatId: Long = 0
}