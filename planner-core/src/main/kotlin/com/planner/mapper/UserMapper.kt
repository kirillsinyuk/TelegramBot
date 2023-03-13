package com.planner.mapper

import com.planner.dto.request.CreateUserRequestDto
import com.planner.dto.response.CreateUserResponseDto
import com.planner.dto.response.GetUserResponseDto
import com.planner.model.User
import org.mapstruct.Mapper

@Mapper
interface UserMapper: MapperConfiguration {

    fun toUser(createUserRequestDto: CreateUserRequestDto): User

    fun toCreateUserResponse(user: User): CreateUserResponseDto

    fun toGetUserResponse(user: User): GetUserResponseDto
}
