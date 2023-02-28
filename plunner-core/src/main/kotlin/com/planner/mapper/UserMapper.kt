package com.planner.mapper

import com.planner.dto.request.CreateUserRequestDto
import com.planner.dto.response.CreateUserResponseDto
import com.planner.model.User
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface UserMapper {

    fun toUser(createUserRequestDto: CreateUserRequestDto): User

    @Mapping(source = "group.id", target = "groupId")
    fun toCreateUserResponse(user: User): CreateUserResponseDto
}
