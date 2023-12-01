package com.kvsinyuk.core.application.impl

import com.kvsinyuk.core.application.exception.NotFoundException
import com.kvsinyuk.core.adapter.out.jpa.repository.UserRepository
import com.kvsinyuk.core.application.port.`in`.GetUserPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetUserPortImpl(
    private val userRepository: UserRepository
): GetUserPort {

    override fun getUserById(id: UUID) =
        userRepository.findById(id)
            .orElseThrow { NotFoundException("User not found") }
}
