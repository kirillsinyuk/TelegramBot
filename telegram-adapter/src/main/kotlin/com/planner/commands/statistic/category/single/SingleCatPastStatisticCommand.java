package com.planner.commands.statistic.category.single;

import com.planner.commands.*;
import com.planner.model.dto.*;
import com.planner.model.enumeration.stats.*;
import com.planner.service.commandService.statistic.divided.*;
import com.planner.service.keyboard.*;
import java.util.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;


@Slf4j
@Component
public class SingleCatPastStatisticCommand extends PlannerBaseCommand {

    @Autowired
    private DividedPastStatisticService statisticService;
    @Autowired
    private TimePeriodKeyboardService timePeriodKeyboard;

    public SingleCatPastStatisticCommand() {
        super("statcatpast", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
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
                sendData(absSender, user, chat, dto);
            }
            sendMsg(absSender, user, chat, "Статистика успешно собрана", timePeriodKeyboard.basicKeyboardMarkup());
        }
    }

    private void sendData(AbsSender absSender, User user, Chat chat, StatisticDto dto) {
        if(dto.getTotalSpend()== null){
            sendMsg(absSender, user, chat, dto.getMessage() + "\nНет трат за данный период.", null);
            return;
        }
        sendMsg(absSender, user, chat, dto.getMessage() + "\nВсего потрачено: " + dto.getTotalSpend().intValue(), null);
        sendPhoto(absSender, user, chat, dto.getStatisticFile());
    }
}
