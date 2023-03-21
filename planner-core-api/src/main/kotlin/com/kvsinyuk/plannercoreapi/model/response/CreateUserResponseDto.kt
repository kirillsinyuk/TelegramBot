package com.kvsinyuk.plannercoreapi.model.response

data class CreateUserResponseDto(
    val id: Long,
    val firstName: String?,
    val lastName: String?
)
