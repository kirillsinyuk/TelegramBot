package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.ProductService;
import com.bot.service.util.CalculateUtils;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@Component
public class GetStatistic extends PlannerBaseCommand {

    @Autowired
    private ProductService productService;

    public GetStatistic() {
        super("getstats", "attributes:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy)");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder message = new StringBuilder();

        //TODO переделать проверку прав
        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length < 2) {
                message.append("You need to use this format:\n /getstats &lt;start&gt; &lt;end&gt;(dd-MM-yyyy)");
            } else {
                try {
                    LocalDateTime startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
                    LocalDateTime endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
                    if (startDate.isAfter(endDate)) {
                        message.append("Первая дата позднее второй!");
                    } else {
                        BigDecimal total = productService.totalSpend(startDate, endDate);
                        productService.getStaticticMsg(startDate, endDate, total);
                        message.append("Всего потрачено : ").append(total);
                    }
                } catch (DateTimeException e) {
                    message.append("Неверный формат дат! Требуется dd-MM-yyyy");
                }
            }
        }

        sendMsg(absSender, user, chat, message.toString());
    }
}
