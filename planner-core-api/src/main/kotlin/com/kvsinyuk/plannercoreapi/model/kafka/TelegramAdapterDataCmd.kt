package com.kvsinyuk.plannercoreapi.model.kafka

import com.kvsinyuk.plannercoreapi.model.kafka.cmd.CommandType

data class TelegramAdapterDataCmd(
    val type: CommandType,
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
    val firstName: String?,
    val lastName: String?
)

data class CreateCategoryCmd(
    val name: String
)

data class AddProductCmd(
    val price: Float,
    val categoryId: Long,
    val description: String? = null
)



