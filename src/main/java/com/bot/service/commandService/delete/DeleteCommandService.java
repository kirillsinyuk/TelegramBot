package com.bot.service.commandService.delete;

import com.bot.model.menu.CommonAction;
import com.bot.service.entity.BotUserService;
import com.bot.service.keyboard.DataKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class DeleteCommandService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;

    public InlineKeyboardMarkup delCommand(String[] args, User user, StringBuilder message){
        message.append("Удалить:");
        return dataKeyboardService.getDataKeyboard(CommonAction.DELETE, botUserService.isAdminOfBand(user));
    }
}
