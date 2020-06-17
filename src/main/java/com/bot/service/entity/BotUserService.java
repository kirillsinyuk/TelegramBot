package com.bot.service.entity;

import com.bot.model.entities.Band;
import com.bot.model.entities.BotUser;
import com.bot.repositories.BotUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;
import java.util.Set;

@Service
public class BotUserService {

    @Autowired
    private BotUserRepository botUserRepository;
    @Autowired
    private BandService bandService;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    public boolean isAdminOfBand(User user) {
        return getBandByUser(user)
                .getAdminId().equals(user.getId());
    }

    @Transactional
    public Band getBandByUser(User user){
        return botUserRepository.getByUserId(user.getId()).getBand();
    }

    public Set<BotUser> getUsersByBand(Band band){
        return botUserRepository.findByBand(band);
    }

    @Transactional
    public BotUser getBotUserByUserId(Integer userId){
        return botUserRepository.getByUserId(userId);
    }

    public boolean isUserExist(int id) {
        return Optional.ofNullable(getUserById(id)).isPresent();
    }

    public boolean isSingleBandMemberIfExist(int id) {
        return isUserExist(id) && getUserById(id).getBand().isSingle();
    }

    private BotUser getUserById(int id){
        return botUserRepository.getByUserId(id);
    }

    public Set<BotUser> getAllBandUsers(Band band){
        return botUserRepository.findByBand(band);
    }

    public void addNewUser(User user, Chat chat){
        BotUser newUser = createBotUser(user, chat.getId());
        categoryService.createCommonCategories(newUser.getBand());
    }

    private BotUser createBotUser(User user, Long chatId) {
        BotUser newUser = BotUser.builder()
                .chatId(chatId)
                .band(bandService.createNewBand(user))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUserName())
                .userId(user.getId())
                .isBot(user.getBot())
                .build();

        return botUserRepository.save(newUser);
    }

    @Transactional
    public BotUser changeUserBand(BotUser user, Band band){
        user.setBand(band);
        return botUserRepository.save(user);
    }
}