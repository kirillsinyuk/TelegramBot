package com.bot.commands.adminCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.BotUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import com.bot.service.util.ParseUtil;

@Slf4j
@Component
public class AddUserCommand extends PlannerBaseCommand {

    public AddUserCommand() {
        super("user_add", "Атрибуты:\n &lt;id&gt; &lt;hasAdminAccess&gt; ");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        if(botService.hasAdminAccess(user.getId())){
            Integer id;
            switch (arguments.length) {
                case 1:
                case 2:
                    id = ParseUtil.getIntFromString(arguments[0]);
                    if (id != null) {
                        botService.addUser(new BotUser(id, null, arguments.length == 2 && arguments[1].equals("true")));
                        message.append(String.format("Пользователь %s успешно добавлен.", arguments[0]));
                    }
                    break;
                default:
                    message.append("You need to use this format:\n /user_add &lt;id&gt; &lt;hasAdminAccess&gt;");
            }
            sendMsg(absSender, user, chat, message.toString(), null);
        }
    }
}
