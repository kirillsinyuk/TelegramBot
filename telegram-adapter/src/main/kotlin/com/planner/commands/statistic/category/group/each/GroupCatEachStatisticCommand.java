package com.planner.commands.statistic.category.group.each;

import com.planner.commands.*;
import com.planner.model.enumeration.stats.*;
import com.planner.service.keyboard.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;


@Slf4j
@Component
public class GroupCatEachStatisticCommand extends PlannerBaseCommand {

    @Autowired
    private StatsKeyboardService<TimeStatisticType> timeKeyboardService;

    public GroupCatEachStatisticCommand() {
        super("statcateach", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    //TODO один в один GroupCatAllStatisticCommand. мб шаблонный метод?
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        InlineKeyboardMarkup keyboardMarkup = timeKeyboardService.getKeyboard(TimeStatisticType.class, arguments[0]);

        sendMsg(absSender, user, chat, "Выберите период", keyboardMarkup);
    }

}
