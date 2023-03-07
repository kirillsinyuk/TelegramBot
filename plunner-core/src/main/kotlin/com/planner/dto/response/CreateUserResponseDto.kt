package com.planner.dto.response

data class CreateUserResponseDto(
    val id: Long,
    val groupId: Long,
    val firstName: String,
    val lastName: String
)
