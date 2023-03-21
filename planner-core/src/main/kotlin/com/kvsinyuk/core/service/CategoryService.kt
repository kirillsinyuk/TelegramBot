package com.kvsinyuk.core.service

import com.kvsinyuk.core.exception.NotFoundException
import com.kvsinyuk.core.model.Category
import com.kvsinyuk.core.model.User
import com.kvsinyuk.core.model.enumeration.CommonCategories
import com.kvsinyuk.core.repository.CategoryRepository
import org.springframework.stereotype.Service

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

    fun getUserCategories(userId: Long) =
        categoryRepository.getCategoriesByUserId(userId)

    fun getCategoryById(id: Long) =
        categoryRepository.getById(id)
            ?: throw NotFoundException("Category not found")

    fun deleteCategoryById(id: Long) {
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
