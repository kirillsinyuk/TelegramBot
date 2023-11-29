package com.kvsinyuk.core.application.port.`in`

import com.kvsinyuk.core.adapter.out.jpa.entity.User
import com.kvsinyuk.v1.http.UserApiProto
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd.CreateUser

interface CreateUserPort {

    fun createUser(request: UserApiProto.CreateUserRequest): User

    fun createUser(request: CreateUser): User
}