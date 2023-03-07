package com.planner.repository

import com.planner.model.User
import com.planner.model.UserGroup
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<UserGroup, Long> {
    @Query("select group from UserGroup group where :user member group.users")
    fun getGroupByMember(user: User): UserGroup?

    fun getUserGroupById(id: Long): UserGroup?

    @Query("select group from UserGroup group join User user where user.id=:userId")
    fun getUserGroupByUserId(userId: Long): UserGroup?
}
