package com.bot.commands.adminCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.entities.BotUser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import com.bot.service.util.ParseUtil;

@Component
public class AddUserCommand extends PlannerBaseCommand {

    public AddUserCommand() {
        super("user_add", "attributes:\n &lt;id&gt; &lt;hasAdminAccess&gt; ");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder addMessage = new StringBuilder();

        if(botService.hasAdminAccess(user.getId())){
            Integer id;
            switch (arguments.length) {
                case 1:
                case 2:
                    id = ParseUtil.getIntFromString(arguments[0]);
                    if (id != null) {
                        botService.addUser(new BotUser(id, null, arguments.length == 2 && arguments[1].equals("true")));
                        addMessage.append(String.format("User %s successfully added", arguments[0]));
                        break;
                    }
                default:
                    addMessage.append("You need to use this format:\n /user_add &lt;id&gt; &lt;hasAdminAccess&gt;");
            }
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(addMessage.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
