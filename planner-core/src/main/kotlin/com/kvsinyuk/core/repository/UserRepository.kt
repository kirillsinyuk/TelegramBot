package com.kvsinyuk.core.repository

import com.kvsinyuk.core.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun getById(id: Long): User?
}
