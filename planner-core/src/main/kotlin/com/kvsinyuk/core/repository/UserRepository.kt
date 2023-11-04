package com.kvsinyuk.core.repository

import com.kvsinyuk.core.model.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, UUID> {
}
