package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.dto.StatisticDto;
import com.bot.service.ProductService;
import com.bot.service.util.DataToImageConverter;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.File;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
public class GetStatistic extends PlannerBaseCommand {

    @Autowired
    private ProductService productService;

    public GetStatistic() {
        super("getstats", "(получить статистику. Без aтрибутов - статистика за месяц.)\nАтрибуты:\n &lt;after&gt; &lt;before&gt; (dd-MM-yyyy).");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());
        StringBuilder message = new StringBuilder();
        LocalDateTime startDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endDate = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        boolean argsIsOk = true;

        //TODO переделать проверку прав
        if (botService.hasAccessToCommands(user.getId())) {
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
                List<StatisticDto> statisticData = productService.getStatistic(startDate, endDate);
                message.append(productService.getStaticticMsg(statisticData, total));
                message.append("Всего потрачено: ").append(total == null ? 0 : total).append(" руб.");
                if(total != null) {
                    File img = DataToImageConverter.convert(statisticData, startDate.toLocalDate(), endDate.toLocalDate());
                    sendPhoto(absSender, user, chat, img);
                }
            }
        }

        sendMsg(absSender, user, chat, message.toString());
    }

    @Scheduled(fixedDelay = 1500000)
    public void scheduledTask() {
        //special for free heroku free dyno
        LOG.info("Bot scheduled action every 25 minutes");
    }
}
