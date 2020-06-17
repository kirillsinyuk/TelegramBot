package com.bot.commands.statistic.dynamics;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.dto.DynamicsDto;
import com.bot.service.commandService.dynamics.UsersDynamicsService;
import com.bot.service.keyboard.TimePeriodKeyboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class StatisticDynCommand extends PlannerBaseCommand {

    @Autowired
    private TimePeriodKeyboardService timePeriodKeyboard;
    @Autowired
    private UsersDynamicsService dynamicsService;

    public StatisticDynCommand() {
        super("statdyn", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        DynamicsDto data = dynamicsService.getUsersDynamics(arguments, user);
        if (data.getDynamicsFile() != null) {
            sendPhoto(absSender, user, chat, data.getDynamicsFile());
        }
        sendMsg(absSender, user, chat, data.getMessage(), timePeriodKeyboard.basicKeyboardMarkup());
    }
}
