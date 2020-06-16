package com.bot.service.botHandlers;

import com.bot.commands.PlannerBaseCommand;
import com.bot.commands.add.AddDataCommand;
import com.bot.commands.add.admin.AddCatCommand;
import com.bot.commands.add.admin.AddUserCommand;
import com.bot.commands.add.admin.CategoryRenameCommand;
import com.bot.commands.common.AddCommand;
import com.bot.commands.common.DeleteCommand;
import com.bot.commands.common.SpendListCommand;
import com.bot.commands.common.HelpCommand;
import com.bot.commands.statistic.StatisticCommand;
import com.bot.commands.delete.DelDataCommand;
import com.bot.commands.delete.admin.DelCatCommand;
import com.bot.commands.delete.admin.DelUserCommand;
import com.bot.commands.statistic.category.StatisticCatCommand;
import com.bot.commands.statistic.category.single.SingleCatCurrentStatisticCommand;
import com.bot.commands.statistic.category.single.SingleCatPastStatisticCommand;
import com.bot.model.menu.CommonAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
public class MenuBotHandler extends AbstractHandler{

    @Autowired
    private SpendListCommand spendListCommand;

    @Autowired
    private AddCommand addCommand;
    @Autowired
    private AddCatCommand addCatCommand;
    @Autowired
    private AddUserCommand addUserCommand;
    @Autowired
    private AddDataCommand addDataCommand;
    @Autowired
    private CategoryRenameCommand categoryRenameCommand;

    @Autowired
    private DeleteCommand deleteCommand;
    @Autowired
    private DelCatCommand delCatCommand;
    @Autowired
    private DelUserCommand delUserCommand;
    @Autowired
    private DelDataCommand delDataCommand;

    @Autowired
    private StatisticCommand statisticCommand;
    @Autowired
    private StatisticCatCommand statisticCatCommand;
    @Autowired
    private SingleCatCurrentStatisticCommand catCurrentStatisticCommand;
    @Autowired
    private SingleCatPastStatisticCommand catPastStatisticCommand;

    @Autowired
    private HelpCommand helpCommand;


    @Override
    public void execute(String[] args, PlannerBaseCommand command, AbsSender absSender, Update update, Message message) {
        command.execute(absSender,
                update.getCallbackQuery().getFrom(),
                update.getCallbackQuery().getMessage().getChat(),
                args);
    }

    protected PlannerBaseCommand getPlannerBaseCommand(String commandArg) {
        if(commandArg.contains(CommonAction.ADD.getName())) {
            return getAddCommands(commandArg);
        } else if(commandArg.contains(CommonAction.DELETE.getName())) {
            return getDelCommands(commandArg);
        }  else if(commandArg.contains(CommonAction.STATISTIC.getName())) {
            return getStatCommands(commandArg);
        }
        switch (commandArg) {
            case "/getprod":
                return spendListCommand;
            default:
                return helpCommand;
        }
    }

    private PlannerBaseCommand getAddCommands(String commandArg) {
        switch (commandArg) {
            case "/add":
                return addCommand;
            case "/addcat":
                return addCatCommand;
            case "/adddata":
                return addDataCommand;
            case "/adduser":
                return addUserCommand;
            case "/addcat_r":
                return categoryRenameCommand;
            default:
                return helpCommand;
        }
    }

    private PlannerBaseCommand getDelCommands(String commandArg) {
        switch (commandArg) {
            case "/del":
                return deleteCommand;
            case "/delcat":
                return delCatCommand;
            case "/deluser":
                return delUserCommand;
            case "/deldata":
                return delDataCommand;
            default:
                return helpCommand;
        }
    }

    private PlannerBaseCommand getStatCommands(String commandArg) {
        switch (commandArg) {
            case "/stat":
                return statisticCommand;
            case "/statcat":
                return statisticCatCommand;
            case "/statcatnow":
                return catCurrentStatisticCommand;
            case "/statcatpast":
                return catPastStatisticCommand;
            default:
                return helpCommand;
        }
    }
}
