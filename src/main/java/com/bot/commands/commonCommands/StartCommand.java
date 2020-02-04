package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class StartCommand extends PlannerBaseCommand {

    public StartCommand() {
        super("start", "new user connected to bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        String message = "Hi, " + user.getFirstName() + "! You've been added to bot users' list!\n" +
                "Please enter '/help' for more information.";

        sendMsg(absSender, user, chat, message);
    }
}
