package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends PlannerBaseCommand {

    private final ICommandRegistry commandRegistry;
    @Autowired
    private Logger LOG;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "list all known com.bot.commands");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append("<b>Available com.bot.commands:</b>\n");
        //TODO разделение для администратора и пользователя
        commandRegistry.getRegisteredCommands()
                .forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
