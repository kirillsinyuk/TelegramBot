package com.bot.service.commandService.statistic.divided;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DividedCurrentStatisticService extends DividedUsersStatistic {

    protected LocalDateTime getStartDate(String period){
        LocalDateTime today = LocalDate.now().atStartOfDay();
        switch (period){
            case "today":
                return today;
            case "week":
                return today.minusWeeks(1);
            case "fortnight":
                return today.minusWeeks(2);
            case "month":
                return today.withDayOfMonth(1);
            case "quarter":
                return today.minusMonths(3).withDayOfMonth(1);
            case "half-year":
                return today.minusMonths(6).withDayOfMonth(1);
            case "all-time":
                return LocalDate.of(2020, 2, 6).atStartOfDay();
            default:
                return today.withDayOfMonth(1);
        }
    }

    protected LocalDateTime getEndDate(String period) {
        return LocalDate.now().plusDays(1).atStartOfDay();
    }

}
