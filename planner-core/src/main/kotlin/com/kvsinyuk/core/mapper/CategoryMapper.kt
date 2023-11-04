package com.kvsinyuk.core.mapper

import com.kvsinyuk.core.model.Category
import com.kvsinyuk.plannercoreapi.model.response.CategoryResponseDto
import com.kvsinyuk.plannercoreapi.model.response.CreateCategoryResponseDto
import com.kvsinyuk.plannercoreapi.model.response.GetCategoriesResponseDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CategoryMapper: MapperConfiguration {

    fun toCategoryCreateResponse(category: Category): CreateCategoryResponseDto

    fun toCategoryResponseDto(category: Set<Category>): Set<CategoryResponseDto>
}

fun CategoryMapper.toGetCategoryResponseDto(categories: Set<Category>) =
    GetCategoriesResponseDto(
        categories = toCategoryResponseDto(categories)
    )
