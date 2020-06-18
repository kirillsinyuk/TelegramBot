package com.bot.service.commandService;

import com.bot.model.entities.BotUser;
import com.bot.model.entities.Product;
import com.bot.model.menu.stats.TimeStatisticType;
import com.bot.repositories.ProductRepository;
import com.bot.service.commandService.statistic.TimePeriodsStatisticImpl;
import com.bot.service.entity.BotUserService;
import com.bot.service.keyboard.TimePeriodKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchasesService extends TimePeriodsStatisticImpl {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TimePeriodKeyboardService keyboardService;
    @Autowired
    private BotUserService botUserService;

    @Transactional
    public InlineKeyboardMarkup getPurchasesCommand(String[] arguments, User user, StringBuilder message) {
        switch (arguments.length) {
            case 1: {
                return keyboardService.timePeriodKeyboard(arguments, message, TimeStatisticType.NOW);
            }
            case 2:
                LocalDateTime startDate = getStartDate(arguments[1]);
                LocalDateTime endDate = getEndDate(arguments[1]);

                List<Product> products = getPurchases(startDate, endDate, botUserService.getBotUserByUserId(user.getId()));
                if (products.size() == 0) {
                    message.append("Ещё нет трат за этот период");
                } else {
                    products.forEach(item -> message.append(item.toString()));
                }
                return keyboardService.basicKeyboardMarkup();
            default:
                return keyboardService.basicKeyboardMarkup();
        }
    }

    @Transactional
    List<Product> getPurchases(LocalDateTime start, LocalDateTime end, BotUser user){
        return productRepository.getAllByDataBetweenAndUserAndDeletedFalseOrderByData (start, end, user);
    }
}
