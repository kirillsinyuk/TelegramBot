package com.kvsinyuk.core.mapper

import com.kvsinyuk.core.model.User
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.CreateUserCmd
import com.kvsinyuk.plannercoreapi.model.request.CreateUserRequestDto
import com.kvsinyuk.plannercoreapi.model.response.CreateUserResponseDto
import com.kvsinyuk.plannercoreapi.model.response.GetUserResponseDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper: MapperConfiguration {

    fun toUser(createUserRequestDto: CreateUserRequestDto): User

    fun toUser(createUserRequestDto: CreateUserCmd): User

    fun toCreateUserResponse(user: User): CreateUserResponseDto

    fun toGetUserResponse(user: User): GetUserResponseDto
}
