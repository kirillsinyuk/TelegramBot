package com.planner.service.commandService.statistic.common;

import com.planner.model.dto.*;
import com.planner.service.commandService.statistic.*;
import com.planner.service.entity.*;
import java.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public abstract class CommonUsersStatistic extends TimePeriodsStatisticImpl {

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
}
