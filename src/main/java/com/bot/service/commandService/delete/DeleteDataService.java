package com.bot.service.commandService.delete;

import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
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
public class DeleteDataService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private ValidService validService;
    @Autowired
    private CategoryService categoryService;

    //но пока пусть будет временный вариант.
    //TODO переделать на хранение в БД
    private Map<Long, Category> categoryCash = new HashMap<>();

    @Transactional
    public InlineKeyboardMarkup deleteCommand(String[] args, User user, StringBuilder message){
        return delCommandHandler(args, botUserService.getBotUserByUserId(user.getId()), message);
    }

    private InlineKeyboardMarkup delCommandHandler(String[] args, BotUser user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Выберите категорию:");
            return dataKeyboardService.categoriesKeyboard(CommonAction.DELETE, DataAction.DATA, user.getBand());
        }

        return delData(args, user, message);
    }

    @Transactional
    InlineKeyboardMarkup delData(String[] args, BotUser user, StringBuilder message) {
        String categoryName = validService.joinArgs(args);
        if (categoryCash.get(user.getId()) == null) {
            return checkAndPutInCash(user, message, categoryName);
        } else {
            args = getArgsWithoutCat(categoryCash.get(user.getId()).getName(), args);
            return delspendingIfValid(args, user, message);
        }
    }

    private String[] getArgsWithoutCat(String categoryName, String[] args) {
        return validService.joinArgs(args).replaceFirst(categoryName, "").trim().split(" ");
    }

    @Transactional
    InlineKeyboardMarkup checkAndPutInCash(BotUser user, StringBuilder message, String categoryName) {
        if (validOldCategory(categoryName, user.getBand().getCategories(), message)) {
            categoryCash.put(user.getId(), categoryService.getCategoryByName(categoryName, user.getBand().getCategories()));
            message.append("Введите цену и коммертарий(опционально)");
            return new InlineKeyboardMarkup();
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }

    private boolean validOldCategory(String category, Set<Category> categories, StringBuilder message){
        if(!validService.validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(!validService.catAlreadyExist(category, categories)){
            message.append("В этой группе нет категории с таким именем.");
            return false;
        }
        return true;
    }

    @Transactional
    InlineKeyboardMarkup delspendingIfValid(String[] args, BotUser user, StringBuilder message) {
        Category category = categoryCash.get(user.getId());

        if(validService.validData(args, category, user.getBand().getCategories(), message)){
            productService.deleteByCategoryAndPrice(category, Integer.parseInt(args[0]), message);
        }
        categoryCash.remove(user.getId());
        return dataKeyboardService.basicKeyboardMarkup();
    }
}
