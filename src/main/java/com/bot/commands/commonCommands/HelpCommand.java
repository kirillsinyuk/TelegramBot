package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.Bot;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class HelpCommand extends PlannerBaseCommand {

    @Autowired
    private Bot bot;


    public HelpCommand() {
        super("help", "list all known com.bot.commands");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append("<b>Available com.bot.commands:</b>\n");
        //TODO разделение для администратора и пользователя
        bot.getRegisteredCommands()
                .forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
