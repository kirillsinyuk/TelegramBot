package com.kvsinyuk.core.repository

import com.kvsinyuk.core.model.Category
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Long> {
    fun getById(id: Long): Category?

    @Query("select category from Category category " +
            "where category.user.id=:userId")
    fun getCategoriesByUserId(userId: Long): Set<Category>
}
