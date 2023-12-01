package com.kvsinyuk.core.adapter.`in`.http

import com.kvsinyuk.core.adapter.mapper.ProductMapper
import com.kvsinyuk.core.application.service.ProductService
import com.kvsinyuk.v1.http.ProductApiProto.GetProductsRequest
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
    fun getProductsByUser(@PathVariable userId: UUID, request: GetProductsRequest) =
        productService.getProducts(userId, request)
            .let { productMapper.toGetProductsResponse(it) }

    @GetMapping("/{userId}/products/byCategories")
    fun getGroupedProductsByUser(@PathVariable userId: UUID, request: GetProductsRequest) =
        productService.getProductsByCategories(userId, request)
            .let { productMapper.toGetProductsResponse(it) }
}
