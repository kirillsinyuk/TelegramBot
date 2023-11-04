package com.kvsinyuk.core.controller

import com.kvsinyuk.core.mapper.ProductMapper
import com.kvsinyuk.core.mapper.toGetProductsResponseDto
import com.kvsinyuk.core.service.ProductService
import com.kvsinyuk.plannercoreapi.model.request.GetProductsRequestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserProductController(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    @GetMapping("/{userId}/products")
    fun getProductsByUser(
        @PathVariable userId: UUID,
        request: GetProductsRequestDto
    ) = productService.getProducts(userId, request)
        .let { productMapper.toGetProductsResponseDto(it) }

    @GetMapping("/{userId}/products/byCategories")
    fun getGroupedProductsByUser(
        @PathVariable userId: UUID,
        request: GetProductsRequestDto
    ) = productService.getProductsByCategories(userId, request)
        .let { productMapper.toGetProductsResponseDto(it) }
}
