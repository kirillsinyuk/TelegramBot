package com.kvsinyuk.plannercoreapi.model.response

data class GetUserResponseDto(
    val id: Long,
    val firstName: String?,
    val lastName: String?
)
