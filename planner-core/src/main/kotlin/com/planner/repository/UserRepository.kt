package com.planner.repository

import com.planner.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun getById(id: Long): User?
}
