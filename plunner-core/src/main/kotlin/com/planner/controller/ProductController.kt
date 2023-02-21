package com.planner.controller

import com.planner.dto.request.CreateProductRequestDto
import com.planner.dto.request.GetProductsRequestDto
import com.planner.mapper.ProductMapper
import com.planner.mapper.toGetProductsResponseDto
import com.planner.service.ProductService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    @PostMapping
    fun addProduct(@RequestBody request: CreateProductRequestDto) =
        productService.addProduct(request)
            .let { productMapper.toCreateProductResponseDto(it) }

    @DeleteMapping
    fun deleteProduct(@RequestBody request: CreateProductRequestDto) =
        productService.addProduct(request)
            .let { productMapper.toCreateProductResponseDto(it) }

    @PostMapping("/all")
    fun getProducts(@RequestBody request: GetProductsRequestDto) =
        productService.getProduct(request)
            .let { productMapper.toGetProductsResponseDto(it) }

}
