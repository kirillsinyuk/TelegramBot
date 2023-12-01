package com.kvsinyuk.core.adapter.mapper

import com.kvsinyuk.core.config.MapperConfiguration
import com.kvsinyuk.core.adapter.out.jpa.entity.Category
import com.kvsinyuk.v1.http.CategoryApiProto.CreateCategoryResponse
import com.kvsinyuk.v1.http.CategoryApiProto.GetCategoriesResponse
import com.kvsinyuk.v1.model.CategoryOuterClass
import org.mapstruct.Mapper


@Mapper(config = MapperConfiguration::class)
abstract class CategoryMapper {

    abstract fun toCategoryCreateResponse(category: Category): CreateCategoryResponse

    abstract fun toCategoryResponse(category: Category): CategoryOuterClass.Category

    fun toGetCategoryResponse(categories: Set<Category>) =
        GetCategoriesResponse.newBuilder()
            .addAllCategories(categories.map { toCategoryResponse(it) })
            .build()

}
