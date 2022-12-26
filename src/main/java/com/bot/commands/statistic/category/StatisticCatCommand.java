package com.bot.commands.statistic.category;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.enumeration.stats.MemberStatisticType;
import com.bot.model.enumeration.stats.TimeStatisticType;
import com.bot.service.entity.BotUserService;
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
public class StatisticCatCommand extends PlannerBaseCommand {

    @Autowired
    private StatsKeyboardService<TimeStatisticType> timeKeyboardService;
    @Autowired
    private StatsKeyboardService<MemberStatisticType> memberKeyboardService;
    @Autowired
    private BotUserService botUserService;

    public StatisticCatCommand() {
        super("statcat", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();
        InlineKeyboardMarkup keyboardMarkup;

        if(botUserService.getBandByUser(user).isSingle()) {
            keyboardMarkup = timeKeyboardService.getKeyboard(TimeStatisticType.class, arguments[0]);
            message.append("Выберите период");
        } else {
            keyboardMarkup = memberKeyboardService.getKeyboard(MemberStatisticType.class, arguments[0]);
            message.append("Какую статистику выхотите получить?");
        }

        sendMsg(absSender, user, chat, message.toString(), keyboardMarkup);
    }
}
