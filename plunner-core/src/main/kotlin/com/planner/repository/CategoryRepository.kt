package com.planner.repository

import com.planner.model.Category
import com.planner.model.UserGroup
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Long> {
    fun getById(id: Long): Category?

    @Query("select category from Category category where category.group.id=:groupId")
    fun getCategoriesByGroupId(groupId: Long): Set<Category>

    fun findByGroup(group: UserGroup): Set<Category>
}
