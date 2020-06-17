package com.bot.service.commandService.add;

import com.bot.model.menu.CommonAction;
import com.bot.service.entity.BotUserService;
import com.bot.service.keyboard.DataKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class AddCommandService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;

    public InlineKeyboardMarkup addCommand(String[] arguments, User user, StringBuilder message){
        message.append("Добавить:");
        return dataKeyboardService.getDataKeyboard(CommonAction.ADD, botUserService.isAdminOfBand(user));
    }

}
