package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.model.dto.StatisticDto;
import com.bot.service.ProductService;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

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

        StringBuilder addMessage = new StringBuilder();

        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length < 2) {
                addMessage.append("You need to use this format:\n /getstats &lt;start&gt; &lt;end&gt;(dd-MM-yyyy)");
            } else {
                try {
                    LocalDateTime startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
                    LocalDateTime endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]);
                    if (startDate.isAfter(endDate)) {
                        addMessage.append("Первая дата позднее второй!");
                    } else {
                        productService.getStatistic(startDate, endDate)
                                .stream()
                                .map(productService::toStatisticsDto)
                                .map(StatisticDto::toString)
                                .forEach(addMessage::append);
                    }
                } catch (DateTimeException e) {
                    addMessage.append("Неверный формат дат! Требуется dd-MM-yyyy");
                }
            }
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(addMessage.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
