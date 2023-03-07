package com.planner.service.commandService.delete;

import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import com.planner.service.util.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
public class DeleteAllDataService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidService validService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BandService bandService;

    @Transactional
    public InlineKeyboardMarkup deleteCommand(String[] args, User user, StringBuilder message){
        return delCommandHandler(args, userService.getUserByUserId(user.getId()), message);
    }

    private InlineKeyboardMarkup delCommandHandler(String[] args, BotUser user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Вы уверены?(Опция удаляет все данные о тратах, включая данные о пользователе)" +
                    "\nВведите Y/N для подтверждения/отказа.");
            return new InlineKeyboardMarkup();
        }

        return delData(args, user, message);
    }

    @Transactional
    InlineKeyboardMarkup delData(String[] args, BotUser user, StringBuilder message) {
        if (args[0].equals("Y")) {
            message.append("Очень жаль расставаться. Если не трудно, напиши @KirillSinyuk о причинах.\nОн много работает над улучшением этого бота. Любая конструктивная критика пойдёт на пользу.");
            return deleteAllData(user);
        } else if(args[0].equals("N")) {
            message.append("Отлично! Я так и знал, что ты передумаешь.");
            return dataKeyboardService.basicKeyboardMarkup();
        } else {
            message.append("Буду считать, что это был ответ \"нет\" С:");
            return dataKeyboardService.basicKeyboardMarkup();
        }
    }

    @Transactional
    InlineKeyboardMarkup deleteAllData(BotUser user) {
        productService.deleteAllSpendings(user);
        bandService.deleteBandWithCategories(user.getBand());
        userService.deleteUser(user);
        return new InlineKeyboardMarkup();
    }
}
