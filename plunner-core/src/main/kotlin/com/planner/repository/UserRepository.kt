package com.planner.repository

import com.planner.model.User
import com.planner.model.UserGroup
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByGroup(group: UserGroup): Set<User>
    fun getById(id: Long): User?
}
