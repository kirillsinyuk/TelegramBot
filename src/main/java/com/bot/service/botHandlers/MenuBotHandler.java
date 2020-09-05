package com.bot.service.botHandlers;

import com.bot.commands.PlannerBaseCommand;
import com.bot.commands.add.AddDataCommand;
import com.bot.commands.add.admin.AddCatCommand;
import com.bot.commands.add.admin.AddUserCommand;
import com.bot.commands.add.admin.CategoryRenameCommand;
import com.bot.commands.add.AddCommand;
import com.bot.commands.delete.DelAllDataCommand;
import com.bot.commands.delete.DeleteCommand;
import com.bot.commands.spendings.SpendListCommand;
import com.bot.commands.common.HelpCommand;
import com.bot.commands.statistic.StatisticCommand;
import com.bot.commands.delete.DelDataCommand;
import com.bot.commands.delete.admin.DelCatCommand;
import com.bot.commands.delete.admin.DelUserCommand;
import com.bot.commands.statistic.category.StatisticCatCommand;
import com.bot.commands.statistic.category.group.all.GroupCatAllNowStatisticCommand;
import com.bot.commands.statistic.category.group.all.GroupCatAllPastStatisticCommand;
import com.bot.commands.statistic.category.group.all.GroupCatAllStatisticCommand;
import com.bot.commands.statistic.category.group.each.GroupCatEachNowStatisticCommand;
import com.bot.commands.statistic.category.group.each.GroupCatEachPastStatisticCommand;
import com.bot.commands.statistic.category.group.each.GroupCatEachStatisticCommand;
import com.bot.commands.statistic.category.single.SingleCatCurrentStatisticCommand;
import com.bot.commands.statistic.category.single.SingleCatPastStatisticCommand;
import com.bot.commands.statistic.dynamics.StatisticDynCommand;
import com.bot.model.enumeration.CommonAction;
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
    private DelAllDataCommand delAllDataCommand;

    @Autowired
    private StatisticCommand statisticCommand;
    @Autowired
    private StatisticCatCommand statisticCatCommand;
    @Autowired
    private SingleCatCurrentStatisticCommand catCurrentStatisticCommand;
    @Autowired
    private SingleCatPastStatisticCommand catPastStatisticCommand;
    @Autowired
    private GroupCatAllStatisticCommand catAllStatisticCommand;
    @Autowired
    private GroupCatAllPastStatisticCommand catAllPastStatisticCommand;
    @Autowired
    private GroupCatAllNowStatisticCommand catAllNowStatisticCommand;
    @Autowired
    private GroupCatEachStatisticCommand groupCatEachStatisticCommand;
    @Autowired
    private GroupCatEachNowStatisticCommand catEachNowStatisticCommand;
    @Autowired
    private GroupCatEachPastStatisticCommand catEachPastStatisticCommand;

    @Autowired
    private StatisticDynCommand statisticDynCommand;

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
        } else if(commandArg.contains(CommonAction.PURCHASES.getName())) {
            return getSpendCommands(commandArg);
        } else {
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
            case "/delall":
                return delAllDataCommand;
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
            case "/statcateach":
                return groupCatEachStatisticCommand;
            case "/statcateachnow":
                return catEachNowStatisticCommand;
            case "/statcateachpast":
                return catEachPastStatisticCommand;
            case "/statcatall":
                return catAllStatisticCommand;
            case "/statcatallnow":
                return catAllNowStatisticCommand;
            case "/statcatallpast":
                return catAllPastStatisticCommand;
            case "/statdyn":
                return statisticDynCommand;
            default:
                return helpCommand;
        }
    }

    private PlannerBaseCommand getSpendCommands(String commandArg) {
        switch (commandArg) {
            case "/getspend":
                return spendListCommand;
            default:
                return helpCommand;
        }
    }
}
