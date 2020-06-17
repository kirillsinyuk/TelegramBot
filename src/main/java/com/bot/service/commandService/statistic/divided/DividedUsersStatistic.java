package com.bot.service.commandService.statistic.divided;

import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.BotUser;
import com.bot.service.commandService.statistic.TimePeriodsStatisticImpl;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.StatisticDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public abstract class DividedUsersStatistic extends TimePeriodsStatisticImpl {

    @Autowired
    private BotUserService botUserService;
    @Autowired
    private StatisticDtoService statisticDtoService;

    @Transactional
    public List<StatisticDto> getUsersStatistic(String[] arguments, User user){
        return getUsersStatisticHandler(arguments, botUserService.getUsersByBand(botUserService.getBandByUser(user)));
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
