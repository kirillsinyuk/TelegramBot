package com.kvsinyuk.telegram.utils

import com.pengrad.telegrambot.model.Message

fun Message.getChatId() = this.chat().id()

fun Message.getUserId() = this.from().id()