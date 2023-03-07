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
public class DeleteCommand extends PlannerBaseCommand {

    @Autowired
    DeleteCommandService deleteCommandService;

    public DeleteCommand() {
        super("del", "(удалить трату)\nАтибуты:\n &lt;category&gt; &lt;price&gt; ");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        InlineKeyboardMarkup ikb = deleteCommandService.delCommand(args, user, message);
        sendMsg(absSender, user, chat, message.toString(), ikb);
    }
}
