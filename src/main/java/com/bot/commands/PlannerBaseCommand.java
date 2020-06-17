package com.bot.commands;

import com.bot.service.entity.BotUserService;
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

import java.io.File;

@Slf4j

public abstract class PlannerBaseCommand extends BotCommand {

    @Autowired
    protected BotUserService botUserService;

    private static final int MAX_MSG_LENGTH = 4096;

    public PlannerBaseCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    protected void sendPhoto(AbsSender absSender, User user, Chat chat, File photo){
        SendPhoto photoMessage = createAndTuneSendPhoto(chat, photo);

        sendImgMsg(absSender, photoMessage, user);
    }

    private SendPhoto createAndTuneSendPhoto(Chat chat, File photo) {
        SendPhoto photoMessage = new SendPhoto();

        photoMessage.setChatId(chat.getId());
        photoMessage.setPhoto(photo);

        return photoMessage;
    }

    private void sendImgMsg(AbsSender sender, SendPhoto message, User user) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error(user.getId() + getCommandIdentifier(), e);
        }
    }

    protected void sendMsg(AbsSender absSender, User user, Chat chat, String message, ReplyKeyboard keyboard){
        SendMessage msg = createAndTuneSendMessage(chat, message, keyboard);

        sendTextMsg(absSender, msg, user);
    }


    private SendMessage createAndTuneSendMessage(Chat chat, String message, ReplyKeyboard keyboard) {
        SendMessage msg = new SendMessage();

        msg.setReplyMarkup(keyboard);
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);
        msg.setText(message);

        return msg;
    }

    private void sendTextMsg(AbsSender sender, SendMessage message, User user) {
        try {
            sendTextMsg(sender, message);
        } catch (TelegramApiException e) {
            log.error(user.getId() + getCommandIdentifier(), e);
        }
    }

    private void sendTextMsg(AbsSender sender, SendMessage message) throws TelegramApiException {
        String temp = message.getText();
        temp = divideMsgIfLong(sender, message, temp);
        sender.execute(message.setText(temp));
    }

    private String divideMsgIfLong(AbsSender sender, SendMessage message, String temp) throws TelegramApiException {
        while (temp.length() > MAX_MSG_LENGTH) {
            int index = 0;
            index = getLineIndexLessThanMax(temp, index);
            sender.execute(message.setText(temp.substring(0, index)));
            temp = temp.substring(index);
        }
        return temp;
    }

    private int getLineIndexLessThanMax(String temp, int index) {
        while (index < MAX_MSG_LENGTH) {
            int temp_index = temp.indexOf('\n', index + 1);
            if (temp_index  < MAX_MSG_LENGTH) {
                index = temp_index;
            } else {
                break;
            }
        }
        return index;
    }

}
