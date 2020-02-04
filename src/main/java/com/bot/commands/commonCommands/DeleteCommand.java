package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.repositories.ProductRepository;
import com.bot.service.ProductService;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class DeleteCommand extends PlannerBaseCommand {

    @Autowired
    ProductService productService;

    public DeleteCommand() {
        super("delete", "Атибуты:\n &lt;category&gt; &lt;price&gt; ");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length < 2) {
                message.append("Нужно использовать такой формат:\n /delete &lt;category&gt; &lt;price&gt;");
            } else {
                try {
                    int price = Integer.parseInt(arguments[1]);
                    productService.deleteByCategoryAndPrice(arguments[0], price);
                    message.append(String.format("Трата %s по цене %s руб. успешно удалена.", arguments[0], arguments[1]));
                } catch (NumberFormatException e) {
                    message.append("Неверный формат цены! Требуется ввести целое число.");
                }
            }
        }

        sendMsg(absSender, user, chat, message.toString());
    }
}
