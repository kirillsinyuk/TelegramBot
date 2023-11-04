package com.kvsinyuk.plannercoreapi.model.kafka.event

import java.math.BigDecimal
import java.util.UUID

data class CoreEvent(
    val requestData: RequestData,
    val createUserCmd: CreateUserCmd? = null,
    val createCategoryCmd: CreateCategoryCmd? = null,
    val addProductCmd: AddProductCmd? = null
)

data class RequestData(
    val chatId: Long,
    val userId: UUID
)

data class CreateUserCmd(
    val id: UUID,
    val firstName: String?,
    val lastName: String?
)

data class CreateCategoryCmd(
    val id: UUID,
    val name: String
)

data class AddProductCmd(
    val price: BigDecimal,
    val categoryId: UUID,
    val description: String? = null
)



