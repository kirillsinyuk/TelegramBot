package com.kvsinyuk.core.adapter.`in`.http

import com.kvsinyuk.core.adapter.mapper.ProductMapper
import com.kvsinyuk.core.application.service.ProductService
import com.kvsinyuk.v1.http.ProductApiProto.CreateProductRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    @PostMapping
    fun addProduct(@RequestBody request: CreateProductRequest) =
        productService.addProduct(request)
            .let { productMapper.toCreateProductResponse(it) }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID) {
        productService.deleteProduct(id)
    }

}
