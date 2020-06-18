package com.bot.commands.statistic.category.group.each;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.dto.StatisticDto;
import com.bot.model.menu.stats.TimeStatisticType;
import com.bot.service.commandService.statistic.divided.DividedPastStatisticService;
import com.bot.service.keyboard.TimePeriodKeyboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;


@Slf4j
@Component
public class GroupCatEachPastStatisticCommand extends PlannerBaseCommand {

    @Autowired
    private TimePeriodKeyboardService timePeriodKeyboard;
    @Autowired
    private DividedPastStatisticService statisticService;

    public GroupCatEachPastStatisticCommand() {
        super("statcateachpast", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        if (arguments.length == 1) {
            InlineKeyboardMarkup ikb = timePeriodKeyboard.timePeriodKeyboard(arguments, message, TimeStatisticType.PAST);
            sendMsg(absSender, user, chat, message.toString(), ikb);
        } else {
            List<StatisticDto> data = statisticService.getUsersStatistic(arguments, user);
            for (StatisticDto dto : data) {
                sendMsg(absSender, user, chat, dto.getMessage() + "\nВсего потрачено: " + dto.getTotalSpend().intValue(), null);
                sendPhoto(absSender, user, chat, dto.getStatisticFile());
            }
            sendMsg(absSender, user, chat, "Статистика успешно собрана", timePeriodKeyboard.basicKeyboardMarkup());
        }
    }
}
