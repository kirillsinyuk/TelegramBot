package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;

@Slf4j
@Component
public class GetCategories extends PlannerBaseCommand {

    public GetCategories() {
        super("cat", "Список доступных категорий.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();
        if (botService.hasAccessToCommands(user.getId())) {
            message.append("<b>Доступные категории трат:</b>\n");
            Arrays.stream(Category.values()).forEach(category -> message.append(Category.getNameByCategory(category)).append("\n"));

            sendMsg(absSender, user, chat, message.toString(), null);
        }
    }
}
