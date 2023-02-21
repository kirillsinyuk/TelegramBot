package com.planner.commands.statistic.category;

import com.planner.commands.*;
import com.planner.model.enumeration.stats.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.bots.*;

@Slf4j
@Component
public class StatisticCatCommand extends PlannerBaseCommand {

    @Autowired
    private StatsKeyboardService<TimeStatisticType> timeKeyboardService;
    @Autowired
    private StatsKeyboardService<MemberStatisticType> memberKeyboardService;
    @Autowired
    private UserService userService;

    public StatisticCatCommand() {
        super("statcat", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();
        InlineKeyboardMarkup keyboardMarkup;

        if(userService.getGroupByUser(user).isSingle()) {
            keyboardMarkup = timeKeyboardService.getKeyboard(TimeStatisticType.class, arguments[0]);
            message.append("Выберите период");
        } else {
            keyboardMarkup = memberKeyboardService.getKeyboard(MemberStatisticType.class, arguments[0]);
            message.append("Какую статистику выхотите получить?");
        }

        sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
    }
}
