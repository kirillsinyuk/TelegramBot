package com.kvsinyuk.core.mapper

import com.kvsinyuk.core.model.Category
import com.planner.dto.response.CategoryResponseDto
import com.planner.dto.response.CreateCategoryResponseDto
import com.planner.dto.response.GetCategoriesResponseDto
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
