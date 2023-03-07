package com.planner.service.commandService.dynamics;

import com.planner.model.dto.*;
import com.planner.service.entity.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;

@Service
public class UsersDynamicsService {

    @Autowired
    private UserService userService;
    @Autowired
    private DynamicsDtoService dynamicsDtoService;

    @Transactional
    public DynamicsDto getUsersDynamics(String[] arguments, User user){
        return getUsersDynamicsHandler(arguments, userService.getUsersByGroup(userService.getGroupByUser(user)));
    }

    @Transactional
    protected DynamicsDto getUsersDynamicsHandler(String[] arguments, Set<BotUser> users){
        return dynamicsDtoService.getDynamicsDto(users);
    }

}
