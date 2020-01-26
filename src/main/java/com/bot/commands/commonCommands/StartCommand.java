package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class StartCommand extends PlannerBaseCommand {

    public StartCommand() {
        super("start", "new user connected to com.bot.model");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        sb.append("Hi, ").append(user.getFirstName()).append("! You've been added to com.bot.model users' list!\n")
                .append("Please enter '/help' for more information.");

        message.setText(sb.toString());
        execute(absSender, message, chat, user);
    }
}
