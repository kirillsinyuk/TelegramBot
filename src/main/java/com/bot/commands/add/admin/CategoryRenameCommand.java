package com.bot.commands.add.admin;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.commandService.add.admin.AddCatRenamedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class CategoryRenameCommand extends PlannerBaseCommand {

    @Autowired
    private AddCatRenamedService addCatRenamedService;

    public CategoryRenameCommand() {
        super("addcat_r", "(Переименовать категорию)\nАтрибуты:\n &lt;old_name&gt; &lt;new_name&gt;");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        InlineKeyboardMarkup keyboardMarkup = addCatRenamedService.catRenameCommand(arguments, user, message);

        sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
    }


}
