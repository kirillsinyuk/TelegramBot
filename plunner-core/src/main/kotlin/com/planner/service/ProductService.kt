package com.planner.service

import com.planner.dto.request.CreateProductRequestDto
import com.planner.dto.request.GetProductsRequestDto
import com.planner.mapper.ProductMapper
import com.planner.model.Product
import com.planner.repository.ProductRepository
import com.planner.repository.inCategories
import com.planner.repository.isBetweenDates
import com.planner.repository.isNotDeleted
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    fun deleteProduct(id: Long) {
        productRepository.deleteProductById(id)
    }

    fun getProduct(request: GetProductsRequestDto): List<Product> {
        val categories = categoryService.getGroupCategories(request.groupId)
            .mapTo(HashSet()) { it.id }

        return productRepository.findAll(
            where(isBetweenDates(request.from.toInstant(), request.to.toInstant()))
                .and(inCategories(categories))
                .and(isNotDeleted())
        )
    }
}

