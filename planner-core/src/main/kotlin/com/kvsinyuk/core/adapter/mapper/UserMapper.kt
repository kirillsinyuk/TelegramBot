package com.kvsinyuk.core.adapter.mapper

import com.kvsinyuk.core.config.MapperConfiguration
import com.kvsinyuk.core.adapter.out.jpa.entity.User
import com.kvsinyuk.v1.http.UserApiProto.CreateUserRequest
import com.kvsinyuk.v1.http.UserApiProto.CreateUserResponse
import com.kvsinyuk.v1.http.UserApiProto.GetUserResponse
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd.CreateUser
import org.mapstruct.Mapper

@Mapper(config = MapperConfiguration::class)
abstract class UserMapper {

    abstract fun toUser(createUserRequest: CreateUserRequest): User

    abstract fun toUser(createUserRequest: CreateUser): User

    abstract fun toCreateUserResponse(user: User): CreateUserResponse

    abstract fun toGetUserResponse(user: User): GetUserResponse
}
