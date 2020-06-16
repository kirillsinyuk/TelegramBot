package com.bot.service.commandService.statistic.common;

import com.bot.model.dto.StatisticDto;
import com.bot.service.entity.StatisticDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public abstract class CommonUsersStatistic {

    @Autowired
    private StatisticDtoService statisticDtoService;

    @Transactional
    public StatisticDto getUsersStatistic(String[] arguments){
        return getUsersStatisticHandler(arguments);
    }

    protected StatisticDto getUsersStatisticHandler(String[] arguments){
        if (arguments.length == 2) {
            return getStatistic(getStartDate(arguments[1]), getEndDate(arguments[1]));
        }
        return null;
    }

    protected StatisticDto getStatistic(LocalDateTime startDate, LocalDateTime endDate) {
        return statisticDtoService.getStaticticDto(startDate, endDate);
    }

    protected abstract LocalDateTime getStartDate(String period);

    protected abstract LocalDateTime getEndDate(String period);
}
