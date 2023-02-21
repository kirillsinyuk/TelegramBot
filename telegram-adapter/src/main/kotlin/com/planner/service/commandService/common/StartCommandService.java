package com.planner.service.commandService.common;

import com.planner.service.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;

@Service
public class StartCommandService {

    @Autowired
    private UserService userService;


    public String startUsageHandler(TelegramUser user, Chat chat) {
        String message;

        if (userService.isUserExist(user.getId())) {
            message = alreadyExistGreeting(user);
        } else {
            message = firstTimeGreeting(user);
            addUserToBot(user, chat);
        }

        return message;
    }

    private void addUserToBot(User user, Chat chat){
        userService.addNewUser(user, chat);
    }

    private String alreadyExistGreeting(User user) {
        if(userService.isSingleBandMemberIfExist(user.getId())) {
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
         if (userService.isGroupAdmin(user)) {
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
        return userService
                .getUserByUserId(userService.getGroupByUser(user).getAdminId())
                .getUsername();
    }

}
