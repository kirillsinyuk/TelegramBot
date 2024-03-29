package com.kvsinyuk.core.adapter.out.jpa.repository

import com.kvsinyuk.core.adapter.out.jpa.entity.Category
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CategoryRepository : CrudRepository<Category, UUID> {

    @Query("select category from Category category " +
            "where category.user.id=:userId")
    fun getCategoriesByUserId(userId: UUID): Set<Category>
}
