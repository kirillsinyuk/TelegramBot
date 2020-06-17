package com.bot.service.botHandlers;

import com.bot.commands.PlannerBaseCommand;
import com.bot.commands.common.HelpCommand;
import com.bot.commands.common.StartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;

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
