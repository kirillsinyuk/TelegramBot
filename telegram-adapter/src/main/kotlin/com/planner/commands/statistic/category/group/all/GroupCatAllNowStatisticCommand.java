package com.planner.commands.statistic.category.group.all;

import com.planner.commands.*;
import com.planner.model.dto.*;
import com.planner.model.enumeration.stats.*;
import com.planner.service.commandService.statistic.common.*;
import com.planner.service.keyboard.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;

@Slf4j
@Component
public class GroupCatAllNowStatisticCommand extends PlannerBaseCommand {

    @Autowired
    private TimePeriodKeyboardService timePeriodKeyboard;
    @Autowired
    private CommonCurrentStatisticService statisticService;

    public GroupCatAllNowStatisticCommand() {
        super("statcatallnow", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();

        if (arguments.length == 1) {
            InlineKeyboardMarkup ikb = timePeriodKeyboard.timePeriodKeyboard(arguments, message, TimeStatisticType.NOW);
            sendMsg(absSender, user, chat, message.toString(), ikb);
        } else {
            StatisticDto data = statisticService.getUsersStatistic(arguments);
            sendData(absSender, user, chat, data);
        }
    }

    private void sendData(AbsSender absSender, User user, Chat chat, StatisticDto data) {
        if(data.getTotalSpend()== null){
            sendMsg(absSender, user, chat, data.getMessage() + "\nНет трат за данный период.", null);
            return;
        }
        if (data.getStatisticFile() != null) {
            sendPhoto(absSender, user, chat, data.getStatisticFile());
        }
        sendMsg(absSender, user, chat, data.getMessage() + "\nВсего потрачено: " + data.getTotalSpend().intValue(), timePeriodKeyboard.basicKeyboardMarkup());
    }
}
