package com.planner.repository

import com.planner.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("update Product product set product.deleted = true")
    fun deleteProductById(id: Long)
}
