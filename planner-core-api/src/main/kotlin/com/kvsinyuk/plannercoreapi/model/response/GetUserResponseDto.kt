package com.kvsinyuk.plannercoreapi.model.response

import java.util.UUID

data class GetUserResponseDto(
    val id: UUID,
    val firstName: String?,
    val lastName: String?
)
