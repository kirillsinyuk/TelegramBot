package com.planner.service.commandService.delete;

import com.planner.model.enumeration.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
public class DeleteCommandService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;

    public InlineKeyboardMarkup delCommand(String[] args, User user, StringBuilder message){
        message.append("Удалить:");
        return dataKeyboardService.getDataKeyboard(CommonAction.DELETE, userService.isGroupAdmin(user));
    }
}
