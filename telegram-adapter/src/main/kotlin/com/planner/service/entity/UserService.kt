package com.planner.service.entity

import com.planner.model.entities.User
import com.planner.model.entities.UserGroup
import com.planner.repository.UserRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Chat
import java.util.*
typealias TelegramUser = org.telegram.telegrambots.meta.api.objects.User

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bandService: BandService,
    private val categoryService: CategoryService
) {
    fun isGroupAdmin(user: TelegramUser) =
        getGroupByUser(user)!!.admin?.userId == user.id

    fun getGroupByUser(user: TelegramUser) =
        getUserByUserId(user.id).group

    fun getUsersByGroup(band: UserGroup) =
        userRepository.findByGroup(band)

    fun isUserExist(id: Int): Boolean {
        return Optional.ofNullable<Any>(getUserById(id)).isPresent
    }

    fun isSingleBandMemberIfExist(id: Int): Boolean {
        return isUserExist(id) && getUserById(id).getBand().isSingle()
    }

    fun getUserByUserId(userId: Long)=
        userRepository.getByUserId(userId)
            ?: throw RuntimeException("This never happen")
    private fun getUserById(id: Long) =
        userRepository.getByUserId(id)
            ?: throw RuntimeException("This never happen")

    fun getAllBandUsers(band: UserGroup?): Set<User> {
        return userRepository.findByGroup(band)
    }

    fun addNewUser(user: TelegramUser, chat: Chat) {
        val newUser: BotUser = createBotUser(user, chat.id)
        categoryService!!.createCommonCategories(newUser.getBand())
    }

    private fun createBotUser(user: TelegramUser, chatId: Long): User {
        val newUser: BotUser = BotUser.builder()
            .chatId(chatId)
            .band(bandService.createNewGroup(user))
            .firstName(user.firstName)
            .lastName(user.lastName)
            .username(user.userName)
            .userId(user.id)
            .isBot(user.getBot())
            .build()
        return userRepository.save<BotUser>(newUser)
    }

    fun changeUserGroup(user: User, group: UserGroup?)=
        user
            .apply { this.group = group }
            .let { userRepository.save(user) }

    fun deleteUser(user: User) {
        userRepository.delete(user)
    }
}