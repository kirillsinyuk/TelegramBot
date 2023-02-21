package com.planner.service

import com.planner.dto.request.CreateUserRequestDto
import com.planner.mapper.UserMapper
import com.planner.model.User
import com.planner.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    private val userGroupService: UserGroupService
) {

    @Transactional
    fun createUser(user: CreateUserRequestDto): User {
        val newGroup = userGroupService.createNewGroup()
        val newUser = userMapper.toUser(user)
            .apply { group = newGroup }
            .let { userRepository.save(it) }
        userGroupService.setGroupAdmin(newGroup, newUser)

        return newUser
    }

//    fun deleteUser(user: User) {
//        userRepository.delete(user)
//    }
//    fun isGroupAdmin(user: TelegramUser) =
//        getGroupByUser(user)!!.admin?.userId == user.id
//
//    fun getGroupByUser(user: TelegramUser) =
//        getUserByUserId(user.id).group
//
//    fun getUsersByGroup(band: UserGroup) =
//        userRepository.findByGroup(band)
//
//    fun isUserExist(id: Int): Boolean {
//        return Optional.ofNullable<Any>(getUserById(id)).isPresent
//    }
//
//    fun isSingleBandMemberIfExist(id: Int): Boolean {
//        return isUserExist(id) && getUserById(id).getBand().isSingle()
//    }
//
//    fun getUserByUserId(userId: Long)=
//        userRepository.getByUserId(userId)
//            ?: throw RuntimeException("This never happen")
//    private fun getUserById(id: Long) =
//        userRepository.getByUserId(id)
//            ?: throw RuntimeException("This never happen")
//
//    fun getAllBandUsers(band: UserGroup?): Set<User> {
//        return userRepository.findByGroup(band)
//    }
//
//    private fun createBotUser(user: TelegramUser, chatId: Long): User {
//        val newUser: BotUser = BotUser.builder()
//            .chatId(chatId)
//            .band(bandService.createNewGroup(user))
//            .firstName(user.firstName)
//            .lastName(user.lastName)
//            .username(user.userName)
//            .userId(user.id)
//            .isBot(user.getBot())
//            .build()
//        return userRepository.save<BotUser>(newUser)
//    }
//
//    fun changeUserGroup(user: User, group: UserGroup?)=
//        user
//            .apply { this.group = group }
//            .let { userRepository.save(user) }
}
