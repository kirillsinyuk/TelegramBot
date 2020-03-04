package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.Action;
import com.bot.service.ProductStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.File;

@Slf4j
@Component
public class GetStatistic extends PlannerBaseCommand {

    @Autowired
    private ProductStatisticService productStatisticService;

    public GetStatistic() {
        super("getstats", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        //TODO переделать проверку прав
        if (botService.hasAccessToCommands(user.getId())) {
            File img = productStatisticService.getExtendedInfo(arguments, message, Action.STATISTIC);
            sendPhoto(absSender, user, chat, img);
            sendMsg(absSender, user, chat, message.toString());
        }
    }
}
