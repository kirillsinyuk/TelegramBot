package com.planner.controller

import com.planner.dto.request.CreateProductRequestDto
import com.planner.mapper.ProductMapper
import com.planner.service.ProductService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.deleteProduct(id)
    }

}
