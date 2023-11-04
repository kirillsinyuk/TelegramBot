package com.kvsinyuk.core.repository

import com.kvsinyuk.core.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ProductRepository : JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query("update Product product set product.deleted = true")
    fun deleteProductById(id: UUID)
}
