package com.kvsinyuk.core.mapper

import com.kvsinyuk.core.model.Category
import com.kvsinyuk.core.model.User
import com.kvsinyuk.core.model.Product
import com.kvsinyuk.v1.http.ProductApiProto.CreateProductRequest
import com.kvsinyuk.v1.http.ProductApiProto.CreateProductResponse
import com.kvsinyuk.v1.http.ProductApiProto.GetProductsResponse
import com.kvsinyuk.v1.model.ProductOuterClass
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(
    uses = [MoneyMapper::class],
    config = MapperConfiguration::class)
abstract class ProductMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "user", target = "author")
    abstract fun toProduct(createProductRequest: CreateProductRequest, category: Category, user: User): Product

    abstract fun toGetProductResponse(products: Product): ProductOuterClass.Product

    abstract fun toCreateProductResponse(product: Product): CreateProductResponse

    fun toGetProductsResponse(productsByCategory: Map<Category, List<Product>>) =
        productsByCategory
            .map { (key, value) -> key.name to value.map { toGetProductResponse(it) } }
            .toMap()

    fun toGetProductsResponse(products: List<Product>): GetProductsResponse =
        GetProductsResponse.newBuilder()
            .addAllProducts(products.map { toGetProductResponse(it) })
            .build()
}
