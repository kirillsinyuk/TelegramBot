package com.kvsinyuk.core.application.port.`in`

import com.kvsinyuk.core.adapter.out.jpa.entity.User
import java.util.*

interface GetUserPort {

    fun getUserById(id: UUID): User
}
