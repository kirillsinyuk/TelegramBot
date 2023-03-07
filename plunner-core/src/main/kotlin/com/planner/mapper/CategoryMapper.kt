package com.planner.mapper

import com.planner.dto.response.CategoryResponseDto
import com.planner.dto.response.CreateCategoryResponseDto
import com.planner.dto.response.GetCategoriesResponseDto
import com.planner.model.Category
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface CategoryMapper {

    fun toCategoryCreateResponse(category: Category): CreateCategoryResponseDto

    fun toCategoryResponseDto(category: Set<Category>): Set<CategoryResponseDto>
}

fun CategoryMapper.toGetCategoryResponseDto(categories: Set<Category>) =
    GetCategoriesResponseDto(
        categories = toCategoryResponseDto(categories)
    )
