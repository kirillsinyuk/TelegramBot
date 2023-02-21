package com.planner.commands.add;

import com.planner.commands.*;
import com.planner.service.commandService.add.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;

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
