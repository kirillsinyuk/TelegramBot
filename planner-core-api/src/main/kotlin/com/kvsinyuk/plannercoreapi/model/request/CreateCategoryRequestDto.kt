package com.kvsinyuk.plannercoreapi.model.request

import java.util.UUID

data class CreateCategoryRequestDto(
    val name: String,
    val userId: UUID
)
