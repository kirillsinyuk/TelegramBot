package com.planner.service.botHandlers;

import com.planner.commands.*;
import com.planner.commands.add.*;
import com.planner.commands.add.admin.*;
import com.planner.commands.common.*;
import com.planner.commands.delete.*;
import com.planner.commands.delete.admin.*;
import com.planner.commands.spendings.*;
import com.planner.commands.statistic.*;
import com.planner.commands.statistic.category.*;
import com.planner.commands.statistic.category.group.all.*;
import com.planner.commands.statistic.category.group.each.*;
import com.planner.commands.statistic.category.single.*;
import com.planner.commands.statistic.dynamics.*;
import com.planner.model.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.bots.*;

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
