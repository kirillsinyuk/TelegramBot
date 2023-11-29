package com.kvsinyuk.core.application.service

import com.kvsinyuk.core.adapter.mapper.ProductMapper
import com.kvsinyuk.core.adapter.out.jpa.repository.ProductRepository
import com.kvsinyuk.core.adapter.out.jpa.repository.inCategories
import com.kvsinyuk.core.adapter.out.jpa.repository.isAfter
import com.kvsinyuk.core.adapter.out.jpa.repository.isBefore
import com.kvsinyuk.core.adapter.out.jpa.repository.isNotDeleted
import com.kvsinyuk.core.adapter.out.jpa.entity.Product
import com.kvsinyuk.core.application.impl.GetUserPortImpl
import com.kvsinyuk.v1.http.ProductApiProto
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import kotlin.collections.HashSet

@Service
class ProductService(
    private val getUserPortImpl: GetUserPortImpl,
    private val categoryService: CategoryService,
    private val productMapper: ProductMapper,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun addProduct(request: ProductApiProto.CreateProductRequest): Product {
        val user = getUserPortImpl.getUserById(UUID.fromString(request.userId))
        val category = categoryService.getCategoryById(UUID.fromString(request.categoryId))
        val product = productMapper.toProduct(request, category, user)

        return productRepository.save(product)
    }

    fun deleteProduct(id: UUID) {
        productRepository.deleteProductById(id)
    }

    fun getProducts(userId: UUID, request: ProductApiProto.GetProductsRequest): List<Product> {
        val categories = categoryService.getUserCategories(userId)
            .mapTo(HashSet()) { it.id }

        return productRepository.findAll(
            where(isAfter(Instant.ofEpochSecond(request.fromDate.seconds)))
                .and(isBefore(Instant.ofEpochSecond(request.toDate.seconds)))
                .and(inCategories(categories))
                .and(isNotDeleted())
        )
    }

    fun getProductsByCategories(userId: UUID, request: ProductApiProto.GetProductsRequest) =
        getProducts(userId, request)
            .groupBy(Product::category)
}
