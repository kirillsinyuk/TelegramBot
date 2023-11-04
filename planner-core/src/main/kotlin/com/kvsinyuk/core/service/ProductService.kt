package com.kvsinyuk.core.service

import com.kvsinyuk.core.mapper.ProductMapper
import com.kvsinyuk.core.repository.ProductRepository
import com.kvsinyuk.core.repository.inCategories
import com.kvsinyuk.core.repository.isAfter
import com.kvsinyuk.core.repository.isBefore
import com.kvsinyuk.core.repository.isNotDeleted
import com.kvsinyuk.core.model.Product
import com.kvsinyuk.plannercoreapi.model.request.CreateProductRequestDto
import com.kvsinyuk.plannercoreapi.model.request.GetProductsRequestDto
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashSet

@Service
class ProductService(
    private val userService: UserService,
    private val categoryService: CategoryService,
    private val productMapper: ProductMapper,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun addProduct(request: CreateProductRequestDto): Product {
        val user = userService.getUserById(request.userId)
        val category = categoryService.getCategoryById(request.categoryId)
        val product = productMapper.toProduct(request, category, user)

        return productRepository.save(product)
    }

    fun deleteProduct(id: UUID) {
        productRepository.deleteProductById(id)
    }

    fun getProducts(userId: UUID, request: GetProductsRequestDto): List<Product> {
        val categories = categoryService.getUserCategories(userId)
            .mapTo(HashSet()) { it.id }

        return productRepository.findAll(
            where(isAfter(request.from?.toInstant()))
                .and(isBefore( request.to?.toInstant()))
                .and(inCategories(categories))
                .and(isNotDeleted())
        )
    }

    fun getProductsByCategories(userId: UUID, request: GetProductsRequestDto) =
        getProducts(userId, request)
            .groupBy(Product::category)
}
