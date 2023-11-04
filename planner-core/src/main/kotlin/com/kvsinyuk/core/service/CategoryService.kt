package com.kvsinyuk.core.service

import com.kvsinyuk.core.exception.NotFoundException
import com.kvsinyuk.core.model.Category
import com.kvsinyuk.core.model.User
import com.kvsinyuk.core.model.enumeration.CommonCategories
import com.kvsinyuk.core.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashSet

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun createCategory(user: User, categoryName: String): Category {
        val category = Category()
            .apply {
                this.name = categoryName
                this.user = user
            }
        return categoryRepository.save(category)
    }

    fun getUserCategories(userId: UUID) =
        categoryRepository.getCategoriesByUserId(userId)

    fun getCategoryById(id: UUID) =
        categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Category not found") }

    fun deleteCategoryById(id: UUID) {
        categoryRepository.deleteById(id)
    }

    fun createDefaultCategories(user: User) {
        val categories = CommonCategories.values()
            .mapTo(HashSet()) {
                Category()
                    .apply {
                        name = it.label
                        this.user = user
                    }
            }
        categoryRepository.saveAll(categories)
    }
}
