package com.bot.service.commandService.common;

import com.bot.service.entity.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class StartCommandService {

    @Autowired
    private BotUserService botUserService;


    public String startUsageHandler(User user, Chat chat) {
        String message;

        if (botUserService.isUserExist(user.getId())) {
            message = alreadyExistGreeting(user);
        } else {
            message = firstTimeGreeting(user);
            addUserToBot(user, chat);
        }

        return message;
    }

    private void addUserToBot(User user, Chat chat){
        botUserService.addNewUser(user, chat);
    }

    private String alreadyExistGreeting(User user) {
        if(botUserService.isSingleBandMemberIfExist(user.getId())) {
            return singleBandMemberGreeting(user);
        } else {
            return bandMemberGreeting(user);
        }
    }

    private String firstTimeGreeting(User user) {
        return String.format("%s, доброго времени суток.\n" +
                "Это бот для учёта трат и вывода статистики.\n" +
                "Введи команду '/help' для получения информации.", user.getFirstName());
    }

    private String singleBandMemberGreeting(User user) {
        return String.format("%s, ты уже пользователь бота.\n" +
                "Ничего нового или полезного эта команда для тебя не сделает. Зачем ты её используешь?\n" +
                "Если нужна помощь, то введи '/help'.", user.getFirstName());
    }

    private String bandMemberGreeting(User user) {
         if (botUserService.isAdminOfBand(user)) {
             return String.format("%s, ты уже пользователь бота.\n" +
                     "Ничего нового или полезного эта команда для тебя не сделает. Зачем ты её используешь?\n" +
                     "Если нужна помощь, то введи '/help'.", user.getFirstName());
         } else {
             return String.format("%s, доброго времени суток.\n" +
                     "Ты уже добавлен в группу пользователя %s!\n" +
                     "Введи команду '/help' для получения информации.", user.getFirstName(), getAdminUsername(user));
         }
    }

    private String getAdminUsername(User user){
        return botUserService
                .getBotUserByUserId(botUserService.getBandByUser(user).getAdminId())
                .getUsername();
    }

}
