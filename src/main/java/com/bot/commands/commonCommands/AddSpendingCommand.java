package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class AddSpendingCommand extends PlannerBaseCommand {

    @Autowired
    private ProductService productService;

    public AddSpendingCommand() {
        super("add", "(добавить трату)\nАтрибуты:\n &lt;category&gt; &lt;price&gt; &lt;description&gt;");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length < 2) {
                message.append("Нужно использовать такой формат:\n /add &lt;category&gt; &lt;price&gt; &lt;description&gt;");
            } else {
                productService.createAndSaveProduct(arguments, user.getUserName());
                message.append(String.format("Трата %s по цене %s успешно добавлена.", arguments[0], arguments[1]));
            }
        }

        sendMsg(absSender, user, chat, message.toString());
    }
}
