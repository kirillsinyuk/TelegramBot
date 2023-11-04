package com.kvsinyuk.plannercoreapi.model.response

import java.util.UUID

data class CreateUserResponseDto(
    val id: UUID,
    val firstName: String?,
    val lastName: String?
)
