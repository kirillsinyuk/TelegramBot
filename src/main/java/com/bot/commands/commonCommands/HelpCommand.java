package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class HelpCommand extends PlannerBaseCommand {

    @Autowired
    private Bot bot;

    public HelpCommand() {
        super("help", "Список доступных команд.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();
        if (botService.hasAccessToCommands(user.getId())) {
            message.append("<b>Доступные команды:</b>\n");
            //TODO разделение команд для администратора и пользователя
            bot.getRegisteredCommands()
                    .forEach(cmd -> message.append(cmd.toString()).append("\n"));

            sendMsg(absSender, user, chat, message.toString());
        }
    }
}
