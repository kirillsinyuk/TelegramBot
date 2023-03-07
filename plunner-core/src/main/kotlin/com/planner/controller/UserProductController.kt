package com.planner.controller

import com.planner.dto.request.GetProductsRequestDto
import com.planner.mapper.ProductMapper
import com.planner.mapper.toGetProductsResponseDto
import com.planner.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserProductController(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    @GetMapping("/{userId}/products")
    fun getProductsByUser(
        @PathVariable userId: Long,
        request: GetProductsRequestDto
    ) = productService.getProducts(userId, request)
        .let { productMapper.toGetProductsResponseDto(it) }

    @GetMapping("/{userId}/products/byCategories")
    fun getGroupedProductsByUser(
        @PathVariable userId: Long,
        request: GetProductsRequestDto
    ) = productService.getProductsByCategories(userId, request)
        .let { productMapper.toGetProductsResponseDto(it) }
}
