package com.planner.service.commandService.add;

import com.planner.model.enumeration.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
public class AddCommandService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;

    public InlineKeyboardMarkup addCommand(String[] arguments, User user, StringBuilder message){
        message.append("Добавить:");
        return dataKeyboardService.getDataKeyboard(CommonAction.ADD, userService.isGroupAdmin(user));
    }

}
