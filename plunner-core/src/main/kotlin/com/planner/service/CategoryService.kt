package com.planner.service

import com.planner.exception.NotFoundException
import com.planner.model.Category
import com.planner.model.User
import com.planner.model.enumeration.CommonCategories
import com.planner.repository.CategoryRepository
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
