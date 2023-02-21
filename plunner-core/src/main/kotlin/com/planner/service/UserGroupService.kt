package com.planner.service

import com.planner.exception.NotFoundException
import com.planner.model.Category
import com.planner.model.User
import com.planner.model.UserGroup
import com.planner.model.enumeration.CommonCategories
import com.planner.repository.GroupRepository
import org.springframework.stereotype.Service
import java.util.HashSet

@Service
class UserGroupService(
    private val groupRepository: GroupRepository
) {
    fun createNewGroup() =
        UserGroup()
            .apply { categories = getDefaultCategories(this) }
            .let { groupRepository.save(it) }

    fun getGroupById(id: Long): UserGroup =
        groupRepository.getUserGroupById(id)
            ?: throw NotFoundException("Group doesn`t exist")

    fun getGroupByUserId(userId: Long): UserGroup =
        groupRepository.getUserGroupByUserId(userId)
            ?: throw NotFoundException("Group doesn`t exist")

    fun deleteGroup(group: UserGroup) {
        groupRepository.delete(group)
    }

    fun setGroupAdmin(group: UserGroup, user: User) =
        group
            .apply { admin = user }
            .let { groupRepository.save(group) }

    private fun getDefaultCategories(group: UserGroup) =
        CommonCategories.values()
            .mapTo(HashSet()) {
                Category().apply {
                    name = it.label
                    this.group = group
                }
            }
}
