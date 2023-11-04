package com.kvsinyuk.core.mapper

import com.kvsinyuk.core.model.Category
import com.kvsinyuk.core.model.User
import com.kvsinyuk.core.model.Product
import com.kvsinyuk.plannercoreapi.model.request.CreateProductRequestDto
import com.kvsinyuk.plannercoreapi.model.response.CreateProductResponseDto
import com.kvsinyuk.plannercoreapi.model.response.GetProductsResponseDto
import com.kvsinyuk.plannercoreapi.model.response.ProductResponseDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ProductMapper: MapperConfiguration {

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "user", target = "author")
    fun toProduct(createProductRequestDto: CreateProductRequestDto, category: Category, user: User): Product

    fun toGetProductResponseDto(products: List<Product>): List<ProductResponseDto>

    fun toCreateProductResponseDto(product: Product): CreateProductResponseDto
}

fun ProductMapper.toGetProductsResponseDto(productsByCategory: Map<Category, List<Product>>) =
    productsByCategory
        .map { (key, value) -> key.name to toGetProductResponseDto(value) }
        .toMap()

fun ProductMapper.toGetProductsResponseDto(products: List<Product>) =
    GetProductsResponseDto(toGetProductResponseDto(products))
