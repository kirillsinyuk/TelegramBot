package com.kvsinyuk.telegram.mapper

import com.kvsinyuk.plannercoreapi.model.kafka.CreateUserCmd
import com.kvsinyuk.plannercoreapi.model.kafka.RequestData
import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.model.TelegramUser
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface UserMapper: MapperConfiguration {

    @Mapping(expression = "java(message.getUserId())", target = "userId")
    @Mapping(expression = "java(message.getChatId())", target = "chatId")
    fun messageToUser(message: Message): TelegramUser

    @Mapping(expression = "java(message.from().firstName())", target = "firstName")
    @Mapping(expression = "java(message.from().lastName())", target = "lastName")
    fun messageToCreateUserCmd(message: Message): CreateUserCmd

    @Mapping(expression = "java(message.getUserId())", target = "userId")
    @Mapping(expression = "java(message.getChatId())", target = "chatId")
    fun messageToRequestData(message: Message): RequestData
}