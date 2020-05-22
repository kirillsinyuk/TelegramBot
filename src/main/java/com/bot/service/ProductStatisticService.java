package com.bot.service;


import com.bot.model.Action;
import com.bot.service.commandService.PurchasesService;
import com.bot.service.commandService.StatisticService;
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

    public InlineKeyboardMarkup getExtendedKeyboard(String[] arguments, StringBuilder message, Action action){
        switch (action){
            case PURCHASES:
                return purchasesService.getPurchasesCommand(arguments, message);
            case STATISTIC:
                return statisticService.statisticKeyboard(arguments, message, Action.STATISTIC);
        }
        return null;
    }

}
