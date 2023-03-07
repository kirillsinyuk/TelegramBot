package com.planner.service.keyboard;

import com.planner.model.enumeration.stats.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;


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
