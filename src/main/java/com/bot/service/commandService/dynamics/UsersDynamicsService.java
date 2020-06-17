package com.bot.service.commandService.dynamics;

import com.bot.model.dto.DynamicsDto;
import com.bot.model.entities.BotUser;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.DynamicsDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Set;

@Service
public class UsersDynamicsService {

    @Autowired
    private BotUserService botUserService;
    @Autowired
    private DynamicsDtoService dynamicsDtoService;

    @Transactional
    public DynamicsDto getUsersDynamics(String[] arguments, User user){
        return getUsersDynamicsHandler(arguments, botUserService.getUsersByBand(botUserService.getBandByUser(user)));
    }

    @Transactional
    protected DynamicsDto getUsersDynamicsHandler(String[] arguments, Set<BotUser> users){
        return dynamicsDtoService.getDynamicsDto(users);
    }

}
