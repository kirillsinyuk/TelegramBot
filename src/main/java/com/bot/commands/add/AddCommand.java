package com.bot.commands.add;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.commandService.add.AddCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class AddCommand extends PlannerBaseCommand {

    @Autowired
    private AddCommandService addCommandService;

    public AddCommand() {
        super("add", "(добавить. Основное меню.)");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        InlineKeyboardMarkup keyboardMarkup = addCommandService.addCommand(arguments, user, message);
        sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
    }


}
