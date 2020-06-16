package com.bot.commands.common;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.commandService.StartCommandService;
import com.bot.service.keyboard.KeyboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class StartCommand extends PlannerBaseCommand {

    public StartCommand() {
        super("start", "new user connected to bot");
    }

    @Autowired
    private StartCommandService startCommandService;
    @Autowired
    private KeyboardService keyboardService;

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        log.info("User {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        String message = startCommandService.startUsageHandler(user, chat);

        sendMsg(absSender, user, chat, message, keyboardService.basicKeyboardMarkup());
    }
}
