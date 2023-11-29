package com.kvsinyuk.core.adapter.out.jpa.repository

import com.kvsinyuk.core.adapter.out.jpa.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, UUID> {
}
