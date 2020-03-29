package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.Action;
import com.bot.service.ProductStatisticService;
import com.bot.service.commandService.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.File;

@Slf4j
@Component
public class StatisticCommand extends PlannerBaseCommand {

    @Autowired
    private ProductStatisticService productStatisticService;
    @Autowired
    private StatisticService statisticService;

    public StatisticCommand() {
        super("getstats", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        //TODO переделать проверку прав
        if (botService.hasAccessToCommands(user.getId())) {
            InlineKeyboardMarkup keyboardMarkup = productStatisticService.getExtendedKeyboard(arguments, message, Action.STATISTIC);
            File pieImg = statisticService.getStatistic(arguments, message);
            if (pieImg != null) {
                sendPhoto(absSender, user, chat, pieImg);
            }
            sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
        }
    }
}
