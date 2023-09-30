package com.kvsinyuk.telegram.application.port.out

import com.kvsinyuk.telegram.domain.command.CreateCoreUserCmd

interface CreateCoreUserPort {

    fun createUser(cmd: CreateCoreUserCmd)
}