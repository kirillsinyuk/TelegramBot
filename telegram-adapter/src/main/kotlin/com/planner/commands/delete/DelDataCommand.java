package com.planner.commands.delete;

import com.planner.commands.*;
import com.planner.service.commandService.delete.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;

@Slf4j
@Component
public class DelDataCommand extends PlannerBaseCommand {

    @Autowired
    private DeleteDataService deleteDataService;

    public DelDataCommand() {
        super("deldata", "(удалить трату)\nАтрибуты:\n &lt;category&gt; &lt;price&gt;");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        InlineKeyboardMarkup keyboardMarkup = deleteDataService.deleteCommand(arguments, user, message);

        sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
    }


}
