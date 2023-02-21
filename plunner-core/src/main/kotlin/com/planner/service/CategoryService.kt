package com.planner.service

import com.planner.dto.request.CreateCategoryRequestDto
import com.planner.exception.NotFoundException
import com.planner.model.Category
import com.planner.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val userGroupService: UserGroupService
) {

    @Transactional
    fun createCategory(request: CreateCategoryRequestDto): Category {
        val group = userGroupService.getGroupByUserId(request.userId)
        val category = Category()
            .apply {
                this.name = request.name
                this.group = group
            }
        return categoryRepository.save(category)
    }

    fun getUserCategories(groupId: Long) =
        categoryRepository.getCategoriesByGroupId(groupId)

    fun getCategoryById(id: Long) =
        categoryRepository.getById(id)
            ?: throw NotFoundException("Category not found")

    fun deleteCategoryById(id: Long) {
        categoryRepository.deleteById(id)
    }
}
