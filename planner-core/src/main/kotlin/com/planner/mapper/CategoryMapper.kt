package com.planner.mapper

import com.planner.dto.response.CategoryResponseDto
import com.planner.dto.response.CreateCategoryResponseDto
import com.planner.dto.response.GetCategoriesResponseDto
import com.planner.model.Category
import org.mapstruct.Mapper


@Mapper
interface CategoryMapper: MapperConfiguration {

    fun toCategoryCreateResponse(category: Category): CreateCategoryResponseDto

    fun toCategoryResponseDto(category: Set<Category>): Set<CategoryResponseDto>
}

fun CategoryMapper.toGetCategoryResponseDto(categories: Set<Category>) =
    GetCategoriesResponseDto(
        categories = toCategoryResponseDto(categories)
    )
