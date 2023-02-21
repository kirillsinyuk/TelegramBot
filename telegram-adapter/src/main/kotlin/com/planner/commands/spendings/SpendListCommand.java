package com.planner.commands.spendings;

import com.planner.commands.*;
import com.planner.service.commandService.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;

@Slf4j
@Component
public class SpendListCommand extends PlannerBaseCommand {

    @Autowired
    private PurchasesService purchasesService;

    public SpendListCommand() {
        super("getspend", "(вывести список всех трат)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy)");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        InlineKeyboardMarkup keyboard = purchasesService.getPurchasesCommand(arguments, user, message);
        sendMsg(absSender, user, chat, message.toString(), keyboard);
    }
}
