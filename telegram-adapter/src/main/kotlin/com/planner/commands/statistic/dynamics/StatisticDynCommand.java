package com.planner.commands.statistic.dynamics;

import com.planner.commands.*;
import com.planner.model.dto.*;
import com.planner.service.commandService.dynamics.*;
import com.planner.service.keyboard.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.*;

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
