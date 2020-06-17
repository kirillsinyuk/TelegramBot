package com.bot.service.commandService.statistic.common;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class CommonPastStatisticService extends CommonUsersStatistic {

    @Override
    public LocalDateTime getStartDate(String period){
        LocalDateTime today = LocalDate.now().atStartOfDay();
        switch (period){
            case "week":
                return today.minusDays(today.getDayOfWeek().ordinal()).minusWeeks(1);
            case "fortnight":
                return today.minusDays(today.getDayOfWeek().ordinal()).minusWeeks(2);
            case "month":
                return today.minusMonths(1).withDayOfMonth(1);
            case "quarter":
                return today.minusMonths(3).withDayOfMonth(1);
            default:
                return today.withDayOfMonth(1);
        }
    }

    @Override
    public LocalDateTime getEndDate(String period) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        switch (period){
            case "week":
                return today.minusDays(today.getDayOfWeek().ordinal());
            case "fortnight":
                return today.minusDays(today.getDayOfWeek().ordinal());
            case "month":
                return today.withDayOfMonth(1);
            case "quarter":
                return today.withDayOfMonth(1);
            default:
                return today.plusDays(1);
        }
    }

}
