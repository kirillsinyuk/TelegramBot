package com.bot.service.commandService.statistic;

import java.time.LocalDateTime;

public interface StatisticDate {

    LocalDateTime getStartDate(String period);

    LocalDateTime getEndDate(String period);
}
