package com.bot.commands.statistic.category.group.all;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.dto.StatisticDto;
import com.bot.model.menu.stats.TimeStatisticType;
import com.bot.service.commandService.statistic.common.CommonPastStatisticService;
import com.bot.service.keyboard.StatsKeyboardService;
import com.bot.service.keyboard.TimePeriodKeyboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;


@Slf4j
@Component
public class GroupCatAllPastStatisticCommand extends PlannerBaseCommand {

    @Autowired
    private TimePeriodKeyboardService timePeriodKeyboard;
    @Autowired
    private CommonPastStatisticService statisticService;

    public GroupCatAllPastStatisticCommand() {
        super("statcatallpast", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        if (arguments.length == 1) {
            InlineKeyboardMarkup ikb = timePeriodKeyboard.timePeriodKeyboard(arguments, new StringBuilder(), TimeStatisticType.PAST);
            sendMsg(absSender, user, chat, null, ikb);;
        } else {
            StatisticDto data = statisticService.getUsersStatistic(arguments);
            if (data.getStatisticFile() != null) {
                sendPhoto(absSender, user, chat, data.getStatisticFile());
            }
            sendMsg(absSender, user, chat, data.getMessage(), timePeriodKeyboard.basicKeyboardMarkup());
        }
    }
}
