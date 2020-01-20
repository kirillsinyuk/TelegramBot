package com.commands.commonCommands;

import com.commands.PlannerBaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import com.commands.service.BotService;

public class DeleteCommand extends PlannerBaseCommand {

    private static final Logger LOG = LoggerFactory.getLogger(HelpCommand.class);
    private final BotService botService;

    public DeleteCommand(BotService botService) {
        super("delete", "attributes:\n &lt;category&gt; &lt;price&gt; ", botService);
        this.botService = botService;
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder addMessage = new StringBuilder();

        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length < 2) {
                addMessage.append("You need to use this format:\n /add &lt;category&gt; &lt;price&gt;");
            } else {
                addMessage.append(String.format("Purchase %s with price %s successfully deleted.", arguments[0], arguments[1]));
                //add purchase to DB
            }
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(addMessage.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
