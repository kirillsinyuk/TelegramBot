package com.bot.commands.statistic;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.menu.stats.CommonStatisticType;
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
public class StatisticCommand extends PlannerBaseCommand {

    @Autowired
    private StatsKeyboardService<CommonStatisticType> statsKeyboardService;

    public StatisticCommand() {
        super("stat", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        InlineKeyboardMarkup keyboardMarkup = statsKeyboardService.getKeyboard(CommonStatisticType.class, arguments[0]);

        sendMsg(absSender, user, chat, "Выберите тип статистики", keyboardMarkup);
    }
}
