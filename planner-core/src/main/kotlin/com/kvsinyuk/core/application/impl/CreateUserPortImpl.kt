package com.kvsinyuk.core.application.impl

import com.kvsinyuk.core.adapter.mapper.UserMapper
import com.kvsinyuk.core.adapter.out.jpa.repository.UserRepository
import com.kvsinyuk.core.application.port.`in`.CreateUserPort
import com.kvsinyuk.core.application.service.CategoryService
import com.kvsinyuk.v1.http.UserApiProto.CreateUserRequest
import com.kvsinyuk.v1.kafka.TelegramAdapterDataCmdProto.TelegramAdapterDataCmd.CreateUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserPortImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    private val categoryService: CategoryService
): CreateUserPort {

    @Transactional
    override fun createUser(request: CreateUserRequest) =
        userMapper.toUser(request)
            .let { userRepository.save(it) }
            .also{ categoryService.createDefaultCategories(it) }

    @Transactional
    override fun createUser(request: CreateUser) =
        userMapper.toUser(request)
            .let { userRepository.save(it) }
            .also{ categoryService.createDefaultCategories(it) }
}
