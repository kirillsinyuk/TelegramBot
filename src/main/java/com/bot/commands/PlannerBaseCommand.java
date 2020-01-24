package com.bot.commands;

import com.bot.model.entities.BotUser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.bot.service.BotService;

public abstract class PlannerBaseCommand extends BotCommand {

    @Autowired
    public BotService botService;
    @Autowired
    public Logger LOG;

    public PlannerBaseCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender sender, SendMessage message, Chat chat, User user) {
        try {
            if (!botService.hasAccessToCommands(user.getId())) {
                LOG.warn("BotUser {} has no access.", user.getId());
                message.setText("Access denied for you. Try to connect @KirillSinyuk for more info.");
            } else {
                BotUser currentUser = botService.getUserById(user.getId());
                currentUser.setTlgUser(user);
                currentUser.setChat(chat);
            }
            sender.execute(message);
        } catch (TelegramApiException e) {
            LOG.error(user.getId() + getCommandIdentifier(), e);
        }
    }

}
