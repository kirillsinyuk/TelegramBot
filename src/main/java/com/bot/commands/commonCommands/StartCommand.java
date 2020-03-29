package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class StartCommand extends PlannerBaseCommand {

    public StartCommand() {
        super("start", "new user connected to bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();
        ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
        if (botService.hasAccessToCommands(user.getId())) {
           message.append("Доброго времени суток, ")
                   .append(user.getFirstName())
                   .append("! Поздравляю, ты есть в списке пользователей!\n")
                   .append("Введи команду '/help' для получения информации.");
        } else {
            message.append("Доброго времени суток, ")
                    .append(user.getFirstName())
                    .append("! К сожалению, тебя нет в списке пользователей с доступом к боту.\n")
                    .append("Обратись к @KirillSinyuk для получения дополнительной информации.");
        }

        sendMsg(absSender, user, chat, message.toString(), rkm);
    }
}
