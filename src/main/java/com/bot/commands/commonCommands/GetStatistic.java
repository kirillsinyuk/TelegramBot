package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.ProductService;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Component
public class GetStatistic extends PlannerBaseCommand {

    @Autowired
    private ProductService productService;

    public GetStatistic() {
        super("getstats", "атрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy)\nбез отрибутов - статистика за месяц.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();
        LocalDateTime startDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endDate = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        boolean argsIsOk = true;

        //TODO переделать проверку прав
        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length == 2) {
                try {
                    startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
                    endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
                    argsIsOk = startDate.isBefore(endDate);
                    if (startDate.isAfter(endDate)) {
                        message.append("Первая дата позднее второй!");
                    }
                } catch (DateTimeException e) {
                    message.append("Неверный формат дат! Требуется dd-MM-yyyy");
                }
            }
            if (argsIsOk) {
                BigDecimal total = productService.totalSpend(startDate, endDate);
                productService.getStaticticMsg(startDate, endDate, total);
                message.append("Всего потрачено : ").append(total);
            }
        }

        sendMsg(absSender, user, chat, message.toString());
    }
}
