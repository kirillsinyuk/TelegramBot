package com.bot.commands;

import com.bot.model.BotUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.bot.service.BotService;

import java.io.File;

@Slf4j
public abstract class PlannerBaseCommand extends BotCommand {

    @Autowired
    public BotService botService;

    public PlannerBaseCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    private void execute(AbsSender sender, SendMessage message, Chat chat, User user) {
        try {
            if (!botService.hasAccessToCommands(user.getId())) {
                log.warn("BotUser {} has no access.", user.getId());
            } else {
                BotUser currentUser = botService.getUserById(user.getId());
                currentUser.setTlgUser(user);
                currentUser.setChat(chat);
            }
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error(user.getId() + getCommandIdentifier(), e);
        }
    }

    private void execute(AbsSender sender, SendPhoto message, Chat chat, User user) {
        try {
            BotUser currentUser = botService.getUserById(user.getId());
            currentUser.setTlgUser(user);
            currentUser.setChat(chat);
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error(user.getId() + getCommandIdentifier(), e);
        }
    }

    public void sendMsg(AbsSender absSender, User user, Chat chat, String message, ReplyKeyboard keyboard){
        SendMessage msg = new SendMessage();
        msg.setReplyMarkup(keyboard);
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);
        msg.setText(message);

        execute(absSender, msg, chat, user);
    }

    public void sendPhoto(AbsSender absSender, User user, Chat chat, File photo){
        SendPhoto photoMessage = new SendPhoto();
        photoMessage.setChatId(chat.getId());
        photoMessage.setPhoto(photo);

        execute(absSender, photoMessage, chat, user);
    }

}
