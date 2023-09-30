package com.kvsinyuk.telegram.mapper

import com.kvsinyuk.plannercoreapi.model.kafka.cmd.CreateUserCmd
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.RequestData
import com.pengrad.telegrambot.model.Message
import com.kvsinyuk.telegram.adapter.out.jpa.model.TelegramUser
import com.kvsinyuk.telegram.config.MapperConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants.ComponentModel

@Mapper(componentModel = ComponentModel.SPRING)
interface UserMapper: MapperConfiguration {

    @Mapping(expression = "java(message.from().id())", target = "userId")
    @Mapping(expression = "java(message.chat().id())", target = "chatId")
    fun messageToUser(message: Message): TelegramUser

    @Mapping(expression = "java(message.from().firstName())", target = "firstName")
    @Mapping(expression = "java(message.from().lastName())", target = "lastName")
    fun messageToCreateUserCmd(message: Message): CreateUserCmd

    @Mapping(expression = "java(message.from().id())", target = "userId")
    @Mapping(expression = "java(message.chat().id())", target = "chatId")
    fun messageToRequestData(message: Message): RequestData
}