package com.planner.service.botHandlers;

import com.planner.commands.*;
import com.planner.service.keyboard.*;
import org.springframework.beans.factory.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.*;

public abstract class AbstractHandler {

    @Autowired
    private KeyboardService keyboardService;

    public void handle(AbsSender absSender, Update update, Message message){
        String[] commandArr = commandArgsSplitter(update, message);

        PlannerBaseCommand command = getPlannerBaseCommand(commandArr[0]);

        execute(commandArr, command, absSender, update, message);

        keyboardService.hideKeyboard(absSender, update);
    }

    String[] commandArgsSplitter(Update update, Message msg){
        if(!update.hasCallbackQuery() && update.hasMessage()){
            return msg.getText().split(" ");
        }
        String commandStr = msg == null ? update.getCallbackQuery().getData()
                : update.getCallbackQuery().getData() + " " + getText(msg);

        return commandStr.split(" ");
    }

    protected abstract PlannerBaseCommand getPlannerBaseCommand(String s);

    protected abstract void execute(String[] args, PlannerBaseCommand command, AbsSender absSender, Update update, Message message);

    private String getText(Message msg) {
        if(msg.hasContact()){
            return msg.getContact().getUserID().toString();
        }
        return msg.getText();
    }
}
