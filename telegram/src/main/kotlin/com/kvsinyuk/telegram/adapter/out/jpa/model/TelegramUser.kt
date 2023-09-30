package com.kvsinyuk.telegram.adapter.out.jpa.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "telegram_user")
class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var userId: Long = 0

    var chatId: Long = 0

    var coreUserId: Long? = null
}