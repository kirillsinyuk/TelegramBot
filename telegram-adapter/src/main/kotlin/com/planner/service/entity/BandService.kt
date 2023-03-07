package com.planner.service.entity

import com.planner.model.entities.User
import com.planner.model.entities.UserGroup
import com.planner.repository.GroupRepository
import org.springframework.stereotype.Service

@Service
class BandService(
    private val groupRepository: GroupRepository
) {
    fun createNewGroup(user: User) =
        UserGroup()
            .apply {
                admin = user
                users = setOf(user)
            }
            .let { groupRepository.save(it) }

//    fun createNewBand(user: TelegramUser): UserGroup {
//        val newBand = UserGroup()
//        newBand.setSingle(true)
//        newBand.setAdminId(user.getUserId())
//        return groupRepository.save(newBand)
//    }

    fun deleteBandWithCategories(band: UserGroup) {
        groupRepository.delete(band)
    }

    fun saveBand(band: UserGroup) {
        groupRepository.save(band)
    }
}