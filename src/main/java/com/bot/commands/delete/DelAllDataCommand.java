package com.bot.commands.delete;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.commandService.delete.DeleteAllDataService;
import com.bot.service.commandService.delete.DeleteDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class DelAllDataCommand extends PlannerBaseCommand {

    @Autowired
    private DeleteAllDataService deleteDataService;

    public DelAllDataCommand() {
        super("delall", "(удалить трату)\nАтрибуты:\n &lt;category&gt; &lt;price&gt;");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        InlineKeyboardMarkup keyboardMarkup = deleteDataService.deleteCommand(arguments, user, message);

        sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
    }


}
