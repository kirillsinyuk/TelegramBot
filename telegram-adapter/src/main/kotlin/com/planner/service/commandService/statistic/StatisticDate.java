package com.planner.service.commandService.statistic;

import java.time.*;

public interface StatisticDate {

    LocalDateTime getStartDate(String period);

    LocalDateTime getEndDate(String period);
}
