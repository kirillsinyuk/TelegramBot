package com.planner.service.commandService;

import com.planner.model.enumeration.stats.*;
import com.planner.repository.*;
import com.planner.service.commandService.statistic.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import java.time.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
public class PurchasesService extends TimePeriodsStatisticImpl {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TimePeriodKeyboardService keyboardService;
    @Autowired
    private UserService userService;

    @Transactional
    public InlineKeyboardMarkup getPurchasesCommand(String[] arguments, User user, StringBuilder message) {
        switch (arguments.length) {
            case 1: {
                return keyboardService.timePeriodKeyboard(arguments, message, TimeStatisticType.NOW);
            }
            case 2:
                LocalDateTime startDate = getStartDate(arguments[1]);
                LocalDateTime endDate = getEndDate(arguments[1]);

                List<Product> products = getPurchases(startDate, endDate, userService.getUserByUserId(user.getId()));
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
