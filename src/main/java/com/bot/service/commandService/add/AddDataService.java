package com.bot.service.commandService.add;

import com.bot.model.entities.Category;
import com.bot.model.entities.Band;
import com.bot.model.entities.BotUser;
import com.bot.model.menu.CommonAction;
import com.bot.model.menu.DataAction;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.CategoryService;
import com.bot.service.entity.ProductService;
import com.bot.service.keyboard.DataKeyboardService;
import com.bot.service.util.ValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AddDataService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private ValidService validService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    //но пока пусть будет временный вариант.
    //TODO переделать на хранение в БД
    private Map<Long, Category> categoryCash = new HashMap<>();

    @Transactional
    public InlineKeyboardMarkup addDataCommand(String[] args, User user, StringBuilder message){
        return addDataHandler(args, botUserService.getBotUserByUserId(user.getId()), message);
    }

    private InlineKeyboardMarkup addDataHandler(String[] args, BotUser user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Выберите категорию:");
            return dataKeyboardService.categoriesKeyboard(CommonAction.ADD, DataAction.DATA, user.getBand());
        }
        return addData(args, user, message);
    }

    @Transactional
    InlineKeyboardMarkup addData(String[] args, BotUser user, StringBuilder message) {
        String categoryName = validService.joinArgs(args);
        if (categoryCash.get(user.getId()) == null) {
            return checkAndPutInCash(user.getBand(), message, categoryName);
        } else {
            return addspendingIfValid(args, user, message);
        }
    }

    @Transactional
    InlineKeyboardMarkup checkAndPutInCash(Band band, StringBuilder message, String categoryName) {
        if (validOldCategory(categoryName, band.getCategories(), message)) {
            categoryCash.put(band.getId(), categoryService.getCategoryByName(categoryName, band.getCategories()));
            message.append("Введите цену и коммертарий(опционально)");
            return new InlineKeyboardMarkup();
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }

    private boolean validOldCategory(String category, Set<com.bot.model.entities.Category> categories, StringBuilder message){
        if(!validService.validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(!validService.catAlreadyExist(category, categories)){
            message.append("Нет этой группе нет категории с таким именем.");
            return false;
        }
        return true;
    }

    @Transactional
    InlineKeyboardMarkup addspendingIfValid(String[] args, BotUser user, StringBuilder message) {
        if(validService.validData(args, user.getBand().getCategories(), message)){
            createAndSaveProduct(args, user);
            message.append(String.format("Трата %s по цене %s успешно добавлена.", args[0], args[1]));
        }
        categoryCash.remove(user.getBand().getId());
        return dataKeyboardService.basicKeyboardMarkup();
    }

    private void createAndSaveProduct(String[] args, BotUser user) throws IllegalArgumentException {
        productService.createAndSaveProduct(args, user);
    }

}
