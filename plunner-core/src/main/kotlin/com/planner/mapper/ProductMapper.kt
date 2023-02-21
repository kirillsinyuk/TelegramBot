package com.planner.mapper

import com.planner.dto.request.CreateProductRequestDto
import com.planner.dto.response.CreateProductResponseDto
import com.planner.dto.response.GetCategoriesResponseDto
import com.planner.dto.response.GetProductsResponseDto
import com.planner.dto.response.ProductResponseDto
import com.planner.model.Category
import com.planner.model.Product
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface ProductMapper {

    @Mapping(source = "category", target = "category")
    fun toProduct(createProductRequestDto: CreateProductRequestDto, category: Category): Product

    fun toGetProductResponseDto(products: List<Product>): List<ProductResponseDto>

    fun toCreateProductResponseDto(product: Product): CreateProductResponseDto
}

fun ProductMapper.toGetProductsResponseDto(products: List<Product>) =
    GetProductsResponseDto(
        products = toGetProductResponseDto(products)
    )
