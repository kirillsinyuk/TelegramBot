package com.planner.service.commandService.statistic.divided;

import com.planner.model.dto.*;
import com.planner.service.commandService.statistic.*;
import com.planner.service.entity.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;

@Service
public abstract class DividedUsersStatistic extends TimePeriodsStatisticImpl {

    @Autowired
    private UserService userService;
    @Autowired
    private StatisticDtoService statisticDtoService;

    @Transactional
    public List<StatisticDto> getUsersStatistic(String[] arguments, User user){
        return getUsersStatisticHandler(arguments, userService.getUsersByGroup(userService.getGroupByUser(user)));
    }

    protected List<StatisticDto> getUsersStatisticHandler(String[] arguments, Set<BotUser> users){
        if (arguments.length == 2) {
            return getStatistic(getStartDate(arguments[1]), getEndDate(arguments[1]), users);
        }
        return null;
    }

    protected List<StatisticDto> getStatistic(LocalDateTime startDate, LocalDateTime endDate, Set<BotUser> users) {
        return users.stream()
                .map(user -> statisticDtoService.getStaticticDto(startDate, endDate, user))
                .collect(Collectors.toList());
    }

}
