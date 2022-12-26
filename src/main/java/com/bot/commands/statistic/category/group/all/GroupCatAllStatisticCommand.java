package com.bot.commands.statistic.category.group.all;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.enumeration.stats.TimeStatisticType;
import com.bot.service.keyboard.StatsKeyboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;


@Slf4j
@Component
public class GroupCatAllStatisticCommand extends PlannerBaseCommand {

    @Autowired
    private StatsKeyboardService<TimeStatisticType> timeKeyboardService;

    public GroupCatAllStatisticCommand() {
        super("statcatall", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        InlineKeyboardMarkup keyboardMarkup = timeKeyboardService.getKeyboard(TimeStatisticType.class, arguments[0]);

        sendMsg(absSender, user, chat, "Выберите период", keyboardMarkup);
    }
}
