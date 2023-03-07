package com.planner.service.botHandlers;

import com.planner.commands.*;
import com.planner.commands.common.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.*;

@Service
public class CommandBotHandler extends AbstractHandler{

    @Autowired
    private HelpCommand helpCommand;
    @Autowired
    private StartCommand startCommand;

    protected PlannerBaseCommand getPlannerBaseCommand(String commandArg) {
        switch (commandArg) {
            case "/start":
                return startCommand;
            case "/help":
                return helpCommand;
            default:
                return helpCommand;
        }
    }

    @Override
    public void execute(String[] args, PlannerBaseCommand command, AbsSender absSender, Update update, Message message) {
        command.execute(absSender,
                update.getMessage().getFrom(),
                update.getMessage().getChat(),
                args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length));
    }
}
