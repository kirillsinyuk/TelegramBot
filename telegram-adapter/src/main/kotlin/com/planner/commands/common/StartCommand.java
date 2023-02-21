package com.planner.commands.common;

import com.planner.commands.*;
import com.planner.service.commandService.common.*;
import com.planner.service.keyboard.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.*;

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
