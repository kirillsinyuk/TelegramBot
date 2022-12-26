package com.bot.service.keyboard;

import com.bot.model.enumeration.stats.CurrentTimePeriod;
import com.bot.model.enumeration.stats.PastTimePeriod;
import com.bot.model.enumeration.stats.TimeStatisticType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


@Service
public class TimePeriodKeyboardService extends KeyboardService {

    @Autowired
    private StatsKeyboardService<CurrentTimePeriod> currentTimeKeyboardService;
    @Autowired
    private StatsKeyboardService<PastTimePeriod> pastTimeKeyboardService;

    public InlineKeyboardMarkup timePeriodKeyboard(String[] args, StringBuilder message, TimeStatisticType type) {
        message.append("Выберите период:");
        switch (type) {
            case NOW:
                return currentTimeKeyboardService.getDataRangeKeyboard(CurrentTimePeriod.class, args[0]);
            case PAST:
                return pastTimeKeyboardService.getDataRangeKeyboard(PastTimePeriod.class, args[0]);
            default:
                return pastTimeKeyboardService.basicKeyboardMarkup();
        }
    }
}
