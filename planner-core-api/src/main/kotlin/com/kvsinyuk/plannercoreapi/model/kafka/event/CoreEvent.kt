package com.kvsinyuk.plannercoreapi.model.kafka.event

import java.math.BigDecimal

data class CoreEvent(
    val requestData: RequestData,
    val createUserCmd: CreateUserCmd? = null,
    val createCategoryCmd: CreateCategoryCmd? = null,
    val addProductCmd: AddProductCmd? = null
)

data class RequestData(
    val chatId: Long,
    val userId: Long
)

data class CreateUserCmd(
    val id: Long,
    val firstName: String?,
    val lastName: String?
)

data class CreateCategoryCmd(
    val id: Long,
    val name: String
)

data class AddProductCmd(
    val price: BigDecimal,
    val categoryId: Long,
    val description: String? = null
)



