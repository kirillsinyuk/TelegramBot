package com.kvsinyuk.core.mapper

import com.kvsinyuk.core.model.User
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd
import com.kvsinyuk.plannercoreapi.model.kafka.event.CoreEvent
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CoreEventMapper: MapperConfiguration {

    @Mapping(source = "user.id", target = "createUserCmd.id")
    @Mapping(source = "user.firstName", target = "createUserCmd.firstName")
    @Mapping(source = "user.lastName", target = "createUserCmd.lastName")
    fun toUserCreateEvent(event: TelegramAdapterDataCmd, user: User): CoreEvent
}
