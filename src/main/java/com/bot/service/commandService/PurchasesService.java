package com.bot.service.commandService;

import com.bot.commands.commonCommands.HelpCommand;
import com.bot.model.Action;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchasesService {

    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private HelpCommand helpCommand;

    public InlineKeyboardMarkup getPurchasesCommand(String[] arguments, StringBuilder message){
        switch (arguments.length ) {
            case 0: {
                return statisticService.statisticKeyboard(arguments, message, Action.PURCHASES);
            }
            case 1:
                LocalDateTime startDate = statisticService.getStartDate(arguments[0]);
                getPurchases(startDate,  LocalDate.now().plusDays(1).atStartOfDay())
                        .forEach(item -> message.append(item.toString()));
                return helpCommand.keyboardMarkup();
            case 2:
                LocalDateTime endDate;
                    try {
                        startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
                        endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
                        if (startDate.isAfter(endDate)) {
                            message.append("Первая дата позднее второй!");
                            return null;
                        }
                    } catch (DateTimeException e) {
                        message.append("Неверный формат дат!(требуется dd-MM-yyyy)");
                        return null;
                    }
                getPurchases(startDate,  endDate)
                        .forEach(item -> message.append(item.toString()));

                return helpCommand.keyboardMarkup();

        }
        return null;
    }

    private List<Product> getPurchases(LocalDateTime start, LocalDateTime end){
        return productRepository.getAllByDataBetweenAndDeletedFalse(start, end);
    }
}
