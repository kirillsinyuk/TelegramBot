package com.bot.service;


import com.bot.model.menu.CommonAction;
import com.bot.service.commandService.PurchasesService;
import com.bot.service.commandService.statistic.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Service
public class ProductStatisticService {

    @Autowired
    private StatisticService statisticService;
    @Autowired
    private PurchasesService purchasesService;

    public InlineKeyboardMarkup getExtendedKeyboard(String[] arguments, StringBuilder message, CommonAction commonAction){
        switch (commonAction){
            case PURCHASES:
                return purchasesService.getPurchasesCommand(arguments, message);
            case STATISTIC:
                return null; //statisticService.timePeriodKeyboard(arguments, message, CommonAction.STATISTIC);
        }
        return null;
    }

}
